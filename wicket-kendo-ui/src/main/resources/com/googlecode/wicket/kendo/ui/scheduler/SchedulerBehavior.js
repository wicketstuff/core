function calculateKendoSchedulerViewEndPeriod(date) {
    var correctedDate = new Date(date);
    var timeMillis = (date.getHours() * kendo.date.MS_PER_HOUR) +
           		 	 (date.getMinutes() * kendo.date.MS_PER_MINUTE) +
           		 	 (date.getSeconds() * 1000);

    if (!timeMillis) {
    	timeMillis = kendo.date.MS_PER_DAY - 1;
    }

    correctedDate.setTime(date.getTime() + timeMillis);

    return correctedDate;
}
