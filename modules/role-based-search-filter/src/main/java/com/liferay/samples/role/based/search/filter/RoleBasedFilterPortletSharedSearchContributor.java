package com.liferay.samples.role.based.search.filter;

import com.liferay.asset.category.property.service.AssetCategoryPropertyLocalService;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Portal;
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

	private final static long VOCABULARY_ID = 37921;
	
	private final static String KEY_ROLE = "ROLE";
	
	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		_log.debug("Contribute role based filter");

		try {
			User user = _portal.getUser(portletSharedSearchSettings.getRenderRequest());
			SearchRequestBuilder searchRequestBuilder =
					portletSharedSearchSettings.getSearchRequestBuilder();
			
			_assetCategoryLocalService.getChildCategories(VOCABULARY_ID).forEach(category -> {
				_log.debug("Adding a rule for category " + category.getName());
				String roleName;
				try {
					roleName = _assetCategoryPropertyLocalService.getCategoryProperty(category.getCategoryId(), KEY_ROLE).getValue();
					if(user.getRoles().stream().anyMatch( role -> {return role.getName().equals(roleName);})) {
						searchRequestBuilder.addComplexQueryPart(
								_complexQueryPartBuilderFactory.builder(
								).field(
									Field.ASSET_CATEGORY_IDS
								).name(
									roleName
								).occur(
									"filter"
								).type(
									"match"
								).value(
									String.valueOf(category.getCategoryId())
								).build());
					}
				} catch (PortalException e) {
					_log.error("Failed to find ROLE property", e);
				}

			});
			
		} catch (PortalException e) {
		}
		
	}

	@Reference
	private ComplexQueryPartBuilderFactory _complexQueryPartBuilderFactory;

	@Reference
	private Portal _portal;

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetCategoryPropertyLocalService _assetCategoryPropertyLocalService;

	private static final Log _log = LogFactoryUtil.getLog(RoleBasedFilterPortletSharedSearchContributor.class);

}
