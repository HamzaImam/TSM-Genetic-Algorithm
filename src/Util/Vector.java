package Util;

public class Vector
{
    public double x, y;


    public Vector()
    {
        set(0, 0);
    }


    public Vector(double x, double y)
    {
        set(x, y);
    }


    public Vector set(double x, double y)
    {
        this.x = x;
        this.y = y;

        return this;
    }


    public Vector set(Vector v)
    {
        x = v.x;
        y = v.y;

        return this;
    }


    public Vector set(double[] source)
    {
        if (source.length == 2) {
            x = source[0];
            y = source[1];
        }
        else
            throw new IllegalArgumentException("Needs only 2 elements");

        return this;
    }


    static public Vector random2D()
    {
        return fromAngle((Math.random() * Math.PI*2));
    }


    static public Vector fromAngle(double angle)
    {
        return new Vector(Math.cos(angle), Math.sin(angle));
    }



    public Vector copy()
    {
        return new Vector(x, y);
    }


    public double mag()
    {
        return Math.sqrt(x*x + y*y);
    }


    public double magSq()
    {
        return (x*x + y*y);
    }

    public Vector add(Vector v)
    {
        x += v.x;
        y += v.y;

        return this;
    }


    public Vector add(double x, double y)
    {
        this.x += x;
        this.y += y;

        return this;
    }


    public Vector sub(Vector v)
    {
        x -= v.x;
        y -= v.y;

        return this;
    }


    public Vector sub(double x, double y)
    {
        this.x -= x;
        this.y -= y;

        return this;
    }


    public Vector mult(double n)
    {
        x *= n;
        y *= n;

        return this;
    }


    public Vector div(double n)
    {
        x /= n;
        y /= n;

        return this;
    }


    public double dot(Vector v)
    {
        return x*v.x + y*v.y;
    }


    public double dot(double x, double y)
    {
        return this.x*x + this.y*y;
    }


    public Vector normalize()
    {
        double m = mag();
        if (m != 0 && m != 1) {
            div(m);
        }

        return this;
    }


    public Vector limit(double max)
    {
        if (magSq() > max*max)
            setMag(max);

        return this;
    }


    public Vector setMag(double len)
    {
        normalize();
        mult(len);

        return this;
    }


    public double heading()
    {
        return Math.atan2(y, x);
    }



    public Vector rotate(double theta)
    {
        double temp = x;

        x = x*Math.cos(theta) - y*Math.sin(theta);
        y = temp*Math.sin(theta) + y*Math.cos(theta);

        return this;
    }


    static public double angleBetween(Vector v1, Vector v2)
    {
        if (v1.x == 0 && v1.y == 0) return 0.0;
        if (v2.x == 0 && v2.y == 0) return 0.0;

        return Math.acos(v1.dot(v2)/(v1.mag()*v2.mag()));
    }


    @Override
    public String toString()
    {
        return "[ " + x + ", " + y + " ]";
    }


    public double[] vectorAsArray()
    {
        return new double[]{x, y};
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector)) {
            return false;
        }
        final Vector p = (Vector) obj;
        return x == p.x && y == p.y;
    }
}
