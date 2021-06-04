/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company. All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package musicbandstory.setup;

import static musicbandstory.constants.MusicbandstoryConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import musicbandstory.constants.MusicbandstoryConstants;
import musicbandstory.service.MusicbandstoryService;


@SystemSetup(extension = MusicbandstoryConstants.EXTENSIONNAME)
public class MusicbandstorySystemSetup
{
	private final MusicbandstoryService musicbandstoryService;

	public MusicbandstorySystemSetup(final MusicbandstoryService musicbandstoryService)
	{
		this.musicbandstoryService = musicbandstoryService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		musicbandstoryService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return MusicbandstorySystemSetup.class.getResourceAsStream("/musicbandstory/sap-hybris-platform.png");
	}
}
