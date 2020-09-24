package cn.jupitermouse.lineage.graph.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cn.jupitermouse.lineage.graph.domain.model.BaseNodeEntity;
import cn.jupitermouse.lineage.graph.domain.model.FieldEntity;
import cn.jupitermouse.lineage.graph.domain.model.FromRelationship;
import cn.jupitermouse.lineage.graph.domain.model.TableEntity;
import cn.jupitermouse.lineage.graph.domain.repository.FromRelationshipRepository;
import cn.jupitermouse.lineage.graph.service.FromRelService;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

/**
 * <p>
 * FromRelService
 * </p>
 *
 * @author JupiterMouse 2020/09/22
 * @since 1.0
 */
@Service
public class FromRelServiceImpl implements FromRelService {

    private final SessionFactory sessionFactory;
    private final FromRelationshipRepository fromRepository;

    public FromRelServiceImpl(SessionFactory sessionFactory,
            FromRelationshipRepository fromRepository) {
        this.sessionFactory = sessionFactory;
        this.fromRepository = fromRepository;
    }

    /**
     * START n=node:node_auto_index(name='Neo'), t=node:node_auto_index(name='The Architect') CREATE UNIQUE
     * n-[r:SPEAKS_WITH]-t RETURN n AS Neo,r
     *
     * @param start 从节点列表  如List<Fields> -> Table
     * @param ends  目标节点 为大一级的节点
     */
    @Override
    public void createNodeFromRel(BaseNodeEntity start, List<BaseNodeEntity> ends) {
        final Session session = sessionFactory.openSession();
        session.query(
                "CREATE (video1:YoutubeVideo1{title:\"Action Movie1\",updated_by:\"Abc\",uploaded_date:\"10/10/2010\"})\n"
                        + "-[movie:ACTION_MOVIES{rating:1}]->\n"
                        + "(video2:YoutubeVideo2{title:\"Action Movie2\",updated_by:\"Xyz\",uploaded_date:\"12/12/2012\"})",
                Collections.emptyMap());
    }

    @Override
    public Result createNodeFromRel(String sql) {
        return sessionFactory.openSession().query(sql, Collections.emptyMap());
    }

    @Override
    public void createTableFromTables(TableEntity table, List<TableEntity> sourceTables) {
        List<FromRelationship> list = sourceTables.stream()
                .map(sourceTable -> FromRelationship.builder().start(table).end(sourceTable).build())
                .collect(Collectors.toList());
        fromRepository.saveAll(list);
    }

    @Override
    public void createFieldFromFields(FieldEntity field, List<FieldEntity> sourceFields) {
        List<FromRelationship> list = sourceFields.stream()
                .map(sourceField -> FromRelationship.builder().start(field).end(sourceField).build())
                .collect(Collectors.toList());
        fromRepository.saveAll(list);
    }
}
