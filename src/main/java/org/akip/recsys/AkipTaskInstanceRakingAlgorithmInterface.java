package org.akip.recsys;

import org.akip.dao.TaskInstanceSearchDTO;

import java.util.List;

public interface AkipTaskInstanceRakingAlgorithmInterface {

    String getName();

    void buildRanking(List<TaskInstanceSearchDTO> tasks);

}
