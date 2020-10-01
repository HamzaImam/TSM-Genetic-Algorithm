package Util;

import java.util.*;

public final class BasicUtility
{
//  Current methods supported are (random, scramble, max, and min)
//  Go to each of the sub headings to see what each of them can do with given parameters

    private BasicUtility() {}
    //The random method
    //Gets random numbers and random elements from data structures
    //-------------------------------------------------------------------------------------
    //random real number
    public static double random(double min, double max)
    {
        if(max>min)
            return Math.random()*(max-min)+ min;
        else
            throw new IllegalArgumentException("Max must be greater than min");
    }

    public static double random(double range)
    {
        if(range>=0)
            return random(0, range);
        else
            return random(range, 0);
    }

    //random integer
    public static int random(int min, int max)
    {
        if(max>min)
            return (int)(Math.random()*(max-min+1)+min);
        else
            throw new IllegalArgumentException("Max must be greater than min");
    }

    public static int random(int range)
    {
        if(range == 0)
            return 0;
        if(range>0)
            return random(0, range);
        else
            return random(range, 0);
    }

    //get random element form a data structure
    public static <E> E random(List<E> list)
    {
        return list.get(random(list.size()-1));
    }

    public static <E> E random(E[] array)
    {
        return array[random(array.length-1)];
    }

    public static double random(double[] array)
    {
        return array[random(array.length-1)];
    }

    public static int random(int[] array)
    {
        return array[random(array.length-1)];
    }

    public static char random(char[] array)
    {
        return array[random(array.length-1)];
    }
    //-------------------------------------------------------------------------------------

    //The scramble method
    //Scrambles elements in a data structure
    //-------------------------------------------------------------------------------------
    public static <E> void scramble(List<E> list)
    {
        int r1;
        int r2;

        E temp;

        for(int i = 0; i < 100; i++)
        {
            r1 = random(list.size()-1);
            r2 = random(list.size()-1);

            temp = list.get(r1);
            list.set(r1, list.get(r2));
            list.set(r2, temp);
        }
    }

    public static <E> void scramble(E[] array)
    {
        int r1;
        int r2;

        E temp;

        for(int i = 0; i < 100; i++)
        {
            r1 = random(array.length-1);
            r2 = random(array.length-1);

            temp = array[r1];
            array[r1] = array[r2];
            array[r2] = temp;
        }
    }

    public static void scramble(int[] array)
    {
        int r1;
        int r2;

        int temp;

        for(int i = 0; i < 100; i++)
        {
            r1 = random(array.length-1);
            r2 = random(array.length-1);

            temp = array[r1];
            array[r1] = array[r2];
            array[r2] = temp;
        }
    }

    public static void scramble(double[] array)
    {
        int r1;
        int r2;

        double temp;

        for(int i = 0; i < 100; i++)
        {
            r1 = random(array.length-1);
            r2 = random(array.length-1);

            temp = array[r1];
            array[r1] = array[r2];
            array[r2] = temp;
        }
    }

    public static void scramble(char[] array)
    {
        int r1;
        int r2;

        char temp;

        for(int i = 0; i < 100; i++)
        {
            r1 = random(array.length-1);
            r2 = random(array.length-1);

            temp = array[r1];
            array[r1] = array[r2];
            array[r2] = temp;
        }
    }
    //-------------------------------------------------------------------------------------

    //The max method
    //Gets the max value from a data structure
    //-------------------------------------------------------------------------------------
    public static int max(int[] array)
    {
        int[] temp = array.clone();
        Arrays.sort(temp);
        return temp[temp.length-1];
    }

    public static double max(double[] array)
    {
        double[] temp = array.clone();
        Arrays.sort(temp);
        return temp[temp.length-1];
    }

    public static int max(List<Integer> list)
    {
        List<Integer> temp = new ArrayList<>(list);
        Collections.sort(temp);
        return temp.get(temp.size()-1);
    }
    //-------------------------------------------------------------------------------------

    //The min method
    //Gets the min value from a data structure
    //-------------------------------------------------------------------------------------
    public static int min(int[] array)
    {
        int[] temp = array.clone();
        Arrays.sort(temp);
        return temp[0];
    }

    public static double min(double[] array)
    {
        double[] temp = array.clone();
        Arrays.sort(temp);
        return temp[0];
    }

    public static int min(List<Integer> list)
    {
        List<Integer> temp = new ArrayList<>(list);
        Collections.sort(temp);
        return temp.get(0);
    }
    //-------------------------------------------------------------------------------------

    public static void swap(int[] array, int index1, int index2)
    {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
}
