package cn.jupitermouse.lineage.graph.model;

import cn.jupitermouse.lineage.graph.constats.NeoConstant;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * <p>
 * 节点间的从属关系 A -> B   field -> table
 * </p>
 *
 * @author JupiterMouse 2020/09/22
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@RelationshipEntity(type = NeoConstant.Graph.REL_OF)
@Deprecated
public class OfRelationship extends BaseEntity {

    /**
     * 关系的开始端
     */
    @StartNode
    private BaseNodeEntity start;

    /**
     * 关系的结束端
     */
    @EndNode
    private BaseNodeEntity end;
}