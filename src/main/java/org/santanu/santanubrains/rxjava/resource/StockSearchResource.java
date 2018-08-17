package org.santanu.santanubrains.rxjava.resource;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ManagedAsync;
import org.santanu.santanubrains.rxjava.exception.InternalErrorException;
import org.santanu.santanubrains.rxjava.log4j.Log4jUtil;
import org.santanu.santanubrains.rxjava.service.StockSearchService;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Path("stocksearch")
public class StockSearchResource {

	private static final Logger logger = Log4jUtil.getLogger(StockSearchResource.class);
	private StockSearchService stockSearchService;

	@Inject
	public StockSearchResource(StockSearchService stockSearchService) {
		super();
		this.stockSearchService = stockSearchService;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getAllStockTickerNameBySearchParam(@Suspended final AsyncResponse async,
			@QueryParam("query") final String query) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockSearchService.getAllStockTickerNameBySearchParam(query).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<String>() {

					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onSuccess(String searchResponse) {
						async.resume(searchResponse);
						outerLatch.countDown();

					}

					@Override
					public void onError(Throwable errorResponse) {
						logger.error(errorResponse);
						async.resume(errorResponse);
						outerLatch.countDown();

					}
				});

		try {
			if (!outerLatch.await(10, TimeUnit.SECONDS)) {
				async.resume(new InternalErrorException());
			}
		} catch (Exception e) {
			async.resume(new InternalErrorException());
		}

	}

}
