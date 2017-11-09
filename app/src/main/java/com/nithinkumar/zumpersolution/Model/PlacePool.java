package com.nithinkumar.zumpersolution.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nithin on 10/31/2017.
 */

public class PlacePool {
    private List<Place> pool;
    private static  PlacePool placePool;
    private int dataCount;

    private PlacePool()
    {
        pool = new ArrayList<>();
    }

    public static PlacePool getInstance()
    {
        if(placePool == null)
        {
            placePool = new PlacePool();
        }
        return placePool;
    }

    public void add(Place place)
    {
        pool.add(place);
    }
    public Place get(int index)
    {
        return pool.get(index);
    }

    public int Count()
    {
        return pool.size();
    }

    public void setDataCount( int val)
    {
        dataCount = val;

    }

    public int getDataCount()
    {
        return dataCount;
    }
}
