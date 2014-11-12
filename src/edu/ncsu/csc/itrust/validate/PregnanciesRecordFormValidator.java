package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.action.ObstetricRecordAction;
import edu.ncsu.csc.itrust.beans.forms.PregnanciesRecordForm;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Validator used in adding obstetric information , {@link ObstetricRecordAction}
 * 
 *  
 * 
 */
public class PregnanciesRecordFormValidator extends BeanValidator<PregnanciesRecordForm> {
	/**
	 * Performs the act of validating the bean in question, which varies depending on the
	 * type of validator.  If the validation does not succeed, a {@link FormValidationException} is thrown.
	 * 
	 * @param p A bean of the type to be validated.
	 */
	@Override
	public void validate(PregnanciesRecordForm bean) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("Year Of Conception", bean.getYearOfConception(), ValidationFormat.YEAR, false));
		errorList.addIfNotNull(checkFormat("Number Of Weeks Pregnant", bean.getNumberOfWeeksPregnant(), ValidationFormat.WEEKANDDAYS, false));
		errorList.addIfNotNull(checkDoubleGreater("Number Of Hours In Labor", Double.toString(bean.getNumberOfHoursInLabor()), 0));
		errorList.addIfNotNull(checkFormat("deliveryType", bean.getDeliveryType(), ValidationFormat.DELIVERYTYPE, false));
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
	
	
}
