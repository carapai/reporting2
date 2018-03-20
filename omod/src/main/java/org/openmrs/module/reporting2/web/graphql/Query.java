package org.openmrs.module.reporting2.web.graphql;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.openmrs.module.reporting2.api.Reporting2Service;
import org.openmrs.module.reporting2.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.openmrs.module.reporting2.utils.Helper.convert;
import static org.openmrs.module.reporting2.utils.Queries.getSummarizedObs;
import static org.openmrs.module.reporting2.utils.Queries.testSqlConnection;

public class Query {

    private final LinkRepository linkRepository;

    @Autowired
    private Reporting2Service reporting2Service;

    public Query(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @GraphQLQuery
    public List<Link> allLinks(LinkFilter filter, @GraphQLArgument(name = "skip", defaultValue = "0") Number skip,
                               @GraphQLArgument(name = "first", defaultValue = "0") Number first) {
        return linkRepository.getAllLinks().subList(skip.intValue(), first.intValue());
    }

    @GraphQLQuery
    public List<Data> allData(List<ResultFilter> filters, @GraphQLArgument(name = "skip", defaultValue = "0") Number skip,
                              @GraphQLArgument(name = "first", defaultValue = "0") Number first) throws SQLException, ClassNotFoundException {
        Connection connection = testSqlConnection();

        StringBuilder condition = new StringBuilder();

        if (filters.size() == 1) {
            condition = new StringBuilder(filters.get(0).getCondition());
        } else if (filters.size() > 1) {
            condition = new StringBuilder("(" + filters.get(0).getCondition() + ")");
            for (ResultFilter filter : filters.subList(1, filters.size())) {
                condition.append(filter.getCondition()).append("(").append(filter.getCondition()).append(")");
            }
        }

        if (skip.intValue() != 0 && first.intValue() != 0) {
            condition.append(" LIMIT ").append(skip).append(",").append(first);
        } else if (first.intValue() != 0) {
            condition.append(" LIMIT ").append(first);
        }
        return convert(getSummarizedObs(condition.toString()));
    }

    @GraphQLQuery
    public List<DataElement> dataElements() {
        return Arrays.asList(new DataElement(), new DataElement());
    }

    @GraphQLQuery
    public List<Category> categories() {
        return Arrays.asList(new Category(), new Category());
    }

    @GraphQLQuery
    public List<CategoryCombination> categoryCombinations() {
        return Arrays.asList(new CategoryCombination(), new CategoryCombination());
    }

    @GraphQLQuery
    public List<CategoryOption> categoryOptions() {
        return reporting2Service.getAllCategoryOptions();
    }

    @GraphQLQuery
    public List<DataSet> dataSets() {
        return Arrays.asList(new DataSet(), new DataSet());
    }

    @GraphQLQuery
    public List<DataElementGroup> dataElementGroups() {
        return Arrays.asList(new DataElementGroup(), new DataElementGroup());
    }

    @GraphQLQuery
    public List<Indicator> indicators() {
        return Arrays.asList(new Indicator(), new Indicator());
    }
}
