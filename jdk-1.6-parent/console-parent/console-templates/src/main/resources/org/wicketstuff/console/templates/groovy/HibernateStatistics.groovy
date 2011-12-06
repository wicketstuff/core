def sf = application.sessionFactory
sf.settings.statisticsEnabled = true
def stats = sf.statistics
stats.statisticsEnabled = true
stats.toString().split(',').each {println it}