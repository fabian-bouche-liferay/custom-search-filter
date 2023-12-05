package com.liferay.samples.role.based.search.filter;

import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property = "javax.portlet.name=" + RoleBasedFilterPortletKeys.ROLE_BASED_FILTER,
		service = PortletSharedSearchContributor.class
	)
public class RoleBasedFilterPortletSharedSearchContributor implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		SearchRequestBuilder searchRequestBuilder =
			portletSharedSearchSettings.getSearchRequestBuilder();

		// Récupérer le userId courant
		// Récupérer ses rôles
		
		// Boucler sur les vocabulaires
		
		String categoryField;
		String queryName;
		String categoryId;
		
		// Ajouter une complex query part pour chaque vocabulaire
		searchRequestBuilder.addComplexQueryPart(
			_complexQueryPartBuilderFactory.builder(
			).field(
				categoryField
			).name(
				queryName
			).occur(
				"filter"
			).type(
				"match"
			).value(
				categoryId
			).build());
	}

	@Reference
	private ComplexQueryPartBuilderFactory _complexQueryPartBuilderFactory;
}
