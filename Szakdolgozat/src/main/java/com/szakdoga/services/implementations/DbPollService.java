package com.szakdoga.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbPollService {

	@Autowired
	private ProductDeactivationService productDeactivationService;

	public void start(int seconds) {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(seconds * 1000);
						productDeactivationService.productDeactivationJob();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}
