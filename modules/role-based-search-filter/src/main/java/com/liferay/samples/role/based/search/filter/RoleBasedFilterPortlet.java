package com.liferay.samples.role.based.search.filter;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.io.IOException;
import java.util.Optional;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.add-default-resource=true",
			"com.liferay.portlet.css-class-wrapper=role-based-filter",
			"com.liferay.portlet.display-category=category.search",
			"com.liferay.portlet.icon=/icons/search.png",
			"com.liferay.portlet.instanceable=false",
			"com.liferay.portlet.layout-cacheable=true",
			"com.liferay.portlet.preferences-owned-by-group=true",
			"com.liferay.portlet.private-request-attributes=false",
			"com.liferay.portlet.private-session-attributes=false",
			"com.liferay.portlet.restore-current-view=false",
			"com.liferay.portlet.use-default-template=true",
			"javax.portlet.display-name=Role Based Filter",
			"javax.portlet.expiration-cache=0",
			"javax.portlet.init-param.template-path=/META-INF/resources/",
			"javax.portlet.init-param.view-template=/view.jsp",
			"javax.portlet.name=" + RoleBasedFilterPortletKeys.ROLE_BASED_FILTER,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=guest,power-user,user"
		},
		service = Portlet.class
	)
public class RoleBasedFilterPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		_log.debug("Render role based portlet");
		
		PortletSharedSearchResponse portletSharedSearchResponse = portletSharedSearchRequest.search(renderRequest);

		super.render(renderRequest, renderResponse);

		_log.debug("Render role based portlet done");

	}

	protected String getPortletId(RenderRequest renderRequest) {
		return portal.getPortletId(renderRequest);
	}

	@Reference
	protected Portal portal;

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

	private static final Log _log = LogFactoryUtil.getLog(RoleBasedFilterPortlet.class);
}
