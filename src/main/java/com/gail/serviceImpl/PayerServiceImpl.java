package com.gail.serviceImpl;

import java.sql.Time;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gail.dao.PayerDao;
import com.gail.dao.RegionDao;
import com.gail.model.Payer;
import com.gail.model.Region;
import com.gail.responseData.UserUploadData;
import com.gail.service.PayerService;
import com.gail.utility.Constants;
import com.gail.utility.ErrorDetails;
import com.gail.utility.ExceptionUtil;
import com.gail.utility.GailNominationServiceException;
import com.gail.utility.UTCDate;

@Service("payerService")
public class PayerServiceImpl extends GenericServiceImpl<Payer, Long> implements PayerService {

	@Autowired
	RegionDao regionDao;

	@Autowired
	PayerDao payerDao;

	@Autowired
	PayerService payerService;

	private static final Logger logger = LoggerFactory.getLogger(PayerServiceImpl.class);

	public Payer savePayerData(UserUploadData uploadData) throws GailNominationServiceException {
		logger.info("saving payer data with id {}", uploadData.getPayerId());

		Region region = null;
		Payer payer = null;
		try {
			region = regionDao.getRegionDetail(uploadData.getRegion());
			payer = payerDao.getPayerByKey(uploadData.getPayerId());
			if (payer == null) {
				payer = new Payer();
				payer.setCreatedDate(UTCDate.getCurrentUTCDate());
			}
			SimpleDateFormat timeformat = new SimpleDateFormat("HH:MM:ss");

			payer.setPayerName(uploadData.getPayerName());
			payer.setPayerKey(uploadData.getPayerId());
			try {
				payer.setCutOffTime(new Time(timeformat.parse(uploadData.getCutOffTime()).getTime()));
			} catch (Throwable ex) {
				ExceptionUtil.handleException(
						new ErrorDetails(Constants.INVALID_CUTOFF_TIME_FORMAT, Constants.ERROR_TYPE_CODE_INTERNAL,
								Constants.ERROR_TYPE_ERROR, "Cutoff time row should be in text format"),
						ex, uploadData.toString());
			}
			payer.setRegionId(region.getRegionId());
			payer.setStatus(Constants.ACTIVE_STATUS);
			payer.setUpdateDate(UTCDate.getCurrentUTCDate());
			payerDao.saveOrUpdate(payer);
		} catch (Throwable ex) {
			ExceptionUtil.handleException(
					new ErrorDetails(Constants.UPLOADING_PAYER_DATA_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
							Constants.ERROR_TYPE_ERROR, "Error while uploading payer data."),
					ex, uploadData.toString());
		}
		return payer;

	}

}