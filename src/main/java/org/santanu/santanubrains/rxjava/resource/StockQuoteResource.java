package org.santanu.santanubrains.rxjava.resource;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ManagedAsync;
import org.santanu.santanubrains.rxjava.exception.InternalErrorException;
import org.santanu.santanubrains.rxjava.log4j.Log4jUtil;
import org.santanu.santanubrains.rxjava.service.StockQuoteService;

import com.google.gson.Gson;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.quotes.stock.StockQuote;

@Path("stock/quote/{symbol}")
public class StockQuoteResource {

	private static final Logger logger = Log4jUtil.getLogger(StockQuoteResource.class);
	private StockQuoteService stockQuoteService;
	private Gson gson;

	@Inject
	public StockQuoteResource(StockQuoteService stockQuoteService, Gson gson) {
		super();
		this.stockQuoteService = stockQuoteService;
		this.gson = gson;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockQuote(@Suspended final AsyncResponse async, @PathParam("symbol") final String symbol) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockQuoteService.getStockQuoteBySymbol(symbol.toUpperCase()).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<StockQuote>() {

					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onSuccess(StockQuote stockQuoteResponse) {
						async.resume(gson.toJson(stockQuoteResponse));
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

	@Path("historicalquote")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockHistoricalQuote(@Suspended final AsyncResponse async,
			@PathParam("symbol") final String symbol) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockQuoteService.getStockHistoricalQuoteBySymbol(symbol.toUpperCase()).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<List<HistoricalQuote>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(List<HistoricalQuote> historicalQuoteResponse) {
						async.resume(gson.toJson(historicalQuoteResponse));
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

	@Path("historicalquote/fromdate")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockHistoricalQuoteFromdate(@Suspended final AsyncResponse async,
			@PathParam("symbol") final String symbol, @QueryParam("fromdate") final String fromDate) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockQuoteService.getStockHistoricalQuoteByFromDate(symbol.toUpperCase(), fromDate).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<List<HistoricalQuote>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(List<HistoricalQuote> historicalQuoteFromDateResponse) {
						async.resume(gson.toJson(historicalQuoteFromDateResponse));
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

	@Path("historicalquote/interval")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockHistoricalQuoteInterval(@Suspended final AsyncResponse async,
			@PathParam("symbol") final String symbol, @QueryParam("interval") final String interval) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockQuoteService.getStockHistoricalQuoteByInterval(symbol.toUpperCase(), interval.toUpperCase())
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<List<HistoricalQuote>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(List<HistoricalQuote> historicalQuoteIntervalRespone) {
						async.resume(gson.toJson(historicalQuoteIntervalRespone));
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

	@Path("historicalquote/fromtodate")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockHistoricalQuoteFromToDate(@Suspended final AsyncResponse async,
			@PathParam("symbol") final String symbol, @QueryParam("fromdate") final String fromDate,
			@QueryParam("todate") final String toDate) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockQuoteService.getStockHistoricalQuoteByFromToDate(symbol.toUpperCase(), fromDate, toDate)
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<List<HistoricalQuote>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(List<HistoricalQuote> historicalQuoteFromToDateResponse) {
						async.resume(gson.toJson(historicalQuoteFromToDateResponse));
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

	@Path("historicalquote/fromdate/interval")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockHistoricalQuoteFromInterval(@Suspended final AsyncResponse async,
			@PathParam("symbol") final String symbol, @QueryParam("fromdate") final String fromDate,
			@QueryParam("interval") final String interval) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockQuoteService.getStockHistoricalQuoteByFromInterval(symbol.toUpperCase(), fromDate, interval.toUpperCase())
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<List<HistoricalQuote>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(List<HistoricalQuote> historicalQuoteFromIntervalResponse) {
						async.resume(gson.toJson(historicalQuoteFromIntervalResponse));
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

	@Path("historicalquote/fromtodate/interval")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockHistoricalQuoteFromToInterval(@Suspended final AsyncResponse async,
			@PathParam("symbol") final String symbol, @QueryParam("fromdate") final String fromDate,
			@QueryParam("todate") final String toDate, @QueryParam("interval") final String interval) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockQuoteService
				.getStockHistoricalQuoteByFromToInterval(symbol.toUpperCase(), fromDate, toDate, interval.toUpperCase())
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<List<HistoricalQuote>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(List<HistoricalQuote> historicalQuoteFromToIntervalResponse) {
						async.resume(gson.toJson(historicalQuoteFromToIntervalResponse));
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
