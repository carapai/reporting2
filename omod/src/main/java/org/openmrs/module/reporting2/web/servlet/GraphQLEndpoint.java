package org.openmrs.module.reporting2.web.servlet;

import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;
import io.leangen.graphql.GraphQLSchemaGenerator;
import org.openmrs.module.reporting2.web.graphql.LinkRepository;
import org.openmrs.module.reporting2.web.graphql.Mutation;
import org.openmrs.module.reporting2.web.graphql.Query;

import java.io.IOException;

public class GraphQLEndpoint extends SimpleGraphQLServlet {
	
	public GraphQLEndpoint() {
		super(buildSchema());
	}
	
	private static GraphQLSchema buildSchema() {
		LinkRepository linkRepository = new LinkRepository();
		Query query = new Query(linkRepository);
		Mutation mutation = new Mutation(linkRepository);
		
		return new GraphQLSchemaGenerator().withOperationsFromSingletons(query, mutation).generate();
	}
}
