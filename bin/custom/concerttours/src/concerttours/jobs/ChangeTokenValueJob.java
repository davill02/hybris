package concerttours.jobs;

import concerttours.service.TokenService;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import javax.annotation.Resource;
import java.util.NoSuchElementException;

public class ChangeTokenValueJob extends AbstractJobPerformable<CronJobModel> {
    @Resource
    private TokenService tokenService;

    @Override
    public PerformResult perform(CronJobModel cronJobModel) {
        try {
            tokenService.generateNewTokenValue();
        }catch (NoSuchElementException exception){
            new PerformResult(CronJobResult.FAILURE, CronJobStatus.FINISHED);
        }
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }
}
