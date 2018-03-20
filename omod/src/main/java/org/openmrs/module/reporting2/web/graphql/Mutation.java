package org.openmrs.module.reporting2.web.graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import io.leangen.graphql.annotations.GraphQLMutation;

public class Mutation implements GraphQLRootResolver {
	
	private final LinkRepository linkRepository;
	
	public Mutation(LinkRepository linkRepository) {
		this.linkRepository = linkRepository;
	}
	
	@GraphQLMutation
	public Link createLink(String url, String description) {
		Link newLink = new Link(url, description);
		linkRepository.saveLink(newLink);
		return newLink;
	}
}
