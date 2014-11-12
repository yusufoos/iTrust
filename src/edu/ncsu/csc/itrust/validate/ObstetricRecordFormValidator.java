package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.action.ObstetricRecordAction;
import edu.ncsu.csc.itrust.beans.forms.ObstetricRecordForm;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Validator used in adding obstetric information , {@link ObstetricRecordAction}
 * 
 *  
 * 
 */
public class ObstetricRecordFormValidator extends BeanValidator<ObstetricRecordForm> {
	/**
	 * Performs the act of validating the bean in question, which varies depending on the
	 * type of validator.  If the validation does not succeed, a {@link FormValidationException} is thrown.
	 * 
	 * @param p A bean of the type to be validated.
	 */
	@Override
	public void validate(ObstetricRecordForm bean) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("LMP", bean.getLMP(), ValidationFormat.DATE, false));
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
	
	
}
