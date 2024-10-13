import logging
from locust import HttpUser, task


class MyLocust(HttpUser):
	connection_timeout = 10.0
	network_timeout = 10.0

	@task
	def problem_list(self):
		self.client.get("/problem?size=10")

	def on_start(self):
		logging.info("START LOCUST")

	def on_stop(self):
		logging.info("STOP LOCUST")
