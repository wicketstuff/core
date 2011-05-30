def sf = application.hibernateSessionFactory
sf.settings.statisticsEnabled = true
def stats = sf.statistics
stats.statisticsEnabled = true
stats.toString().split(',').each {println it}