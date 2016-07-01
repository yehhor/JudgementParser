package repository;

import model.Judge;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yehor on 01.07.2016.
 */
public class MockJudgeRepository {

    private static final Set<Judge> judgeList = new HashSet<Judge>();

    public boolean save(Judge judge)
    {
        return judgeList.add(judge);
    }

    public Set<Judge> getAll()
    {
        return judgeList;
    }

    //ToDo update/delete/get
    public boolean update(Judge judge, Integer id)
    {
        return false;
    }

    public boolean delete(Integer id)
    {
        return false;
    }

    public boolean get(Integer id)
    {
        return false;
    }
}
