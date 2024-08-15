package com.chuwa;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Object o = new Object() {
            public boolean equals(Object t) {
                return t.equals("hi");
            }
        };
        System.out.println(o.equals("hi"));

    }
}
