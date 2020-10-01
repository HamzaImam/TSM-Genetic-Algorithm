import javax.swing.*;


import java.awt.*;

import java.awt.event.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import static Util.GraphicsUtility.*;
import static Util.BasicUtility.*;

public class Main
{
    public static void main(String[] args)
    {
        try {
            random(0);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        int numC;
        try {
            numC = Integer.parseInt(JOptionPane.showInputDialog("Enter number of cities"));
        } catch (NumberFormatException e){
            numC = 5;
        }


        JFrame F = createNewWindow("TSM Genetic Algorithm", 800, 800, true);

        Render renderer = new Render(F.getWidth(), F.getHeight(), numC, true);
        renderer.setBackground(Color.black);

        renderer.addMouseWheelListener(renderer);
        renderer.addMouseMotionListener(renderer);
        renderer.addMouseListener(renderer);

        F.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE)
                    renderer.show = !renderer.show;
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        Timer a = new Timer(0, e -> renderer.repaint());
        a.start();

        F.add(renderer);
        F.setVisible(true);
    }
}


class Render extends JPanel implements MouseWheelListener, MouseMotionListener, MouseListener
{
    public boolean show;

    private final int width, hieght;

    private final boolean fast;

    private final int numberOfCities;
    private final String totalPossiblePaths;

    private double zoom;
    private Point origin;

    private Point[] cities;
    private int[][] population;
    private double[] fitness;

    private final int popSize;

    private int generation;

    private int[] bestOrder;
    private double bestDistance;

    private int[] currentBest;

    private double sure;

    public Render(int width, int height, int num, boolean fast)
    {
        show = false;

        this.width = width;
        this.hieght = height;

        this.fast = fast;

        zoom = 1.0;
        origin = new Point(0, 0);

        numberOfCities = num;


        NumberFormat formatter = new DecimalFormat("0.######E0", DecimalFormatSymbols.getInstance(Locale.ROOT));
        String str = formatter.format(factorial(numberOfCities));
        totalPossiblePaths = str;

        popSize = 500;

        generation = 0;

        cities = new Point[numberOfCities];
        population = new int[popSize][cities.length];
        fitness = new double[popSize];

        bestOrder = new int[cities.length];
        bestDistance = Double.MAX_VALUE;

        currentBest = new int[cities.length];

        sure = 0.0;

        int[] order = new int[cities.length];

        for(int i = 0; i < cities.length; i++)
        {
            cities[i] = new Point(random(width - 20), random(height - 20));
            order[i] = i;
        }

        for(int i = 0; i < population.length; i++)
        {
            population[i] = order.clone();
            scramble(population[i]);
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        sure = Math.round(10000 * (bestDistance + 1)/(distance(cities, currentBest) + 1))/100.0;

        g.setColor(Color.RED);
        g.drawString("Showing best generation: " + show, 0, g.getFont().getSize());
        g.drawString("Generation: " + generation, 0, 2 * g.getFont().getSize());
        g.drawString("Zoom: " + zoom, 0, 3 * g.getFont().getSize());
        g.drawString(sure + "% sure", 0, 4 * g.getFont().getSize());

        g.setColor(Color.BLUE.brighter());
        g.drawString(numberOfCities + " points", getWidth() - g.getFontMetrics().stringWidth(numberOfCities + " points") - 5, g.getFont().getSize());
        g.drawString(totalPossiblePaths + " possible paths", getWidth() - g.getFontMetrics().stringWidth(totalPossiblePaths + " possible paths") - 5, 2 * g.getFont().getSize());


        Graphics2D g2D = (Graphics2D)g;
        g2D.translate(origin.x, origin.y);
        g2D.scale(zoom, zoom);

        if (fast) {
            for (int j = 0; j < 100; j++) {

                g.setColor(Color.WHITE);
                for (int i = 0; i < numberOfCities; i++) {
                    g.fillOval(cities[i].x - 5, cities[i].y - 5, 10, 10);
                }

                calcFitness();
                normalizeF();
                nextGeneration();

                if (show) {
                    for (int i = 0; i < currentBest.length - 1; i++) {
                        Point cityA = cities[currentBest[i]];
                        Point cityB = cities[currentBest[i + 1]];

                        g.drawLine(cityA.x, cityA.y, cityB.x, cityB.y);
                    }
                }

                g.setColor(Color.GREEN);

                for (int i = 0; i < bestOrder.length - 1; i++) {
                    Point cityA = cities[bestOrder[i]];
                    Point cityB = cities[bestOrder[i + 1]];

                    g.drawLine(cityA.x, cityA.y, cityB.x, cityB.y);
                }
            }
        }
    }

    private BigInteger factorial(int n)
    {
        if(n == 1)
            return BigInteger.ONE;
        return BigInteger.valueOf(n).multiply(factorial(n-1));
    }

    private void calcFitness()
    {
        double currentD = Double.MAX_VALUE;
        double d;
        for(int i = 0; i < popSize; i++)
        {
            d = distance(cities, population[i]);
            if(d < bestDistance)
            {
                bestOrder = population[i].clone();
                bestDistance = d;
            }
            if(d < currentD)
            {
                currentBest = population[i].clone();
                currentD = d;
            }

            fitness[i] = 1 / (Math.pow(d, 8) + 1);
        }
    }

    private void normalizeF()
    {
        double sum = 0;
        for(int i = 0; i < fitness.length; i++)
            sum += fitness[i];
        for(int i = 0; i < fitness.length; i++)
            fitness[i] = fitness[i]/sum;
    }

    private void nextGeneration()
    {
        generation++;
        int[][] newPop = new int[popSize][numberOfCities];
        int[] parentA, parentB;
        int[] child;
        for(int i = 0; i < newPop.length; i++)
        {
            parentA = pick(population, fitness);
            parentB = pick(population, fitness);

            child = crossover(parentA, parentB);
            mutate(child, 0.01);
            newPop[i] = child;
        }

        population = newPop;
    }

    private int[] pick(int[][] pop, double[] fitness)
    {
        int index = 0;
        double r = random(1.0);

        while(r > 0)
        {
            r = r - fitness[index];
            index++;
        }
        index--;

        return pop[index].clone();
    }

    private void mutate(int[] dna, double mutationRate)
    {
        for(int i = 0; i < dna.length; i++)
            if(random(1.0) < mutationRate) {
                int indexA = random(dna.length - 1);
                int indexB = random(dna.length - 1);
                swap(dna, indexA, indexB);
            }
    }

    private int[] crossover(int[] parentA, int[] parentB)
    {
        ArrayList<Integer> tChild;
        int temp;

        int start = random(parentA.length - 2);
        int end;
        if(parentA.length != 1)
            end = random(start + 1, parentA.length);
        else
            end = 0;

        Integer[] Temp = new Integer[parentA.length];
        for(int i = 0; i < Temp.length; i++)
            Temp[i] = parentA[i];

        tChild = new ArrayList<Integer>(Arrays.asList(Arrays.copyOfRange(Temp, start, end)));


        for(int i = 0; i < parentB.length; i++)
        {
            temp = parentB[i];
            if(!tChild.contains(temp))
                tChild.add(temp);
        }

        Object[] fTemp = tChild.toArray();

        int[] child = new int[parentA.length];
        for(int i = 0; i < child.length; i++)
            child[i] = (Integer)fTemp[i];

        return child;
    }

    private double distance(Point[] cities, int[] order)
    {
        double sum = 0;

        for(int i = 0; i < order.length - 1; i++)
        {
            Point cityA = cities[order[i]];
            Point cityB = cities[order[i+1]];
            sum += cityA.distanceSq(cityB);
        }

        return sum;
    }

    private int dx, dy;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double zoomFactor = - 0.1*e.getPreciseWheelRotation()*zoom;
        zoom = Math.abs(zoom + zoomFactor);
        origin.x = e.getPoint().x + dx;
        origin.y = e.getPoint().y + dy;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point pOrigin = new Point(origin);
        Point temp = e.getPoint();
        dx = pOrigin.x - temp.x;
        dy = pOrigin.y - temp.y;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        origin.x = e.getPoint().x + dx;
        origin.y = e.getPoint().y + dy;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}