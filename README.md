# stock-finanacequotes-api
A restful webservice implementation in jax-rs framework with asynchronous processing.Technology stack include Rxjava, Google guava for centralize data caching to maintain request uptime and so on.  

The Api consists of several end points for fetching stock related data : 

SINGLE  STOCK DETAILS:--

1.	GET     http://ipaddress:portno/api/stock/GOOG   
2.	GET     http://ipaddress:portno/api/stock/GOOG/includehistorical?include=true
3.	GET     http://ipaddress:portno/api/stock/GOOG/fromdate?fromdate=2017-01-01
4.	GET     http://ipaddress:portno/api/stock/GOOG/interval?interval=WEEKLY (DAILY,MONTHLY)
5.	GET     http://ipaddress:portno/api/stock/GOOG/fromtodate?fromdate=2018-01-01&todate=2018-02-01
6.	GET     http://ipaddress:portno/api/stock/GOOG/frominterval?fromdate=2018-01-01&interval=MONTHLY  (DAILY, WEEKLY)
7.	GET     http://ipaddress:portno/api/stock/GOOG/fromtointerval?fromdate=2018-01-01&todate=2018-02-10&interval=MONTHLY (DAILY, WEEKLY)
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

MULTIPLE  STOCK DETAILS:--

8.	POST   http://ipaddress:portno/api/stocks   {"stockSymbols":["GOOG","TSLA"]}
9.	POST   http://ipaddress:portno/api/stocks/includehistorical?include=true  {"stockSymbols":["AAPL","GOOG","TSLA","BABA"]}
10.	POST   http://ipaddress:portno/api/stocks/fromdate?fromdate=2017-01-01    {"stockSymbols":["GOOG","TSLA"]}
11.	POST   http://ipaddress:portno/api/stocks/interval?interval=weekly  (DAILY, MONTHLY) {"stockSymbols":["GOOG","TSLA"]}
12.	POST   http://ipaddress:portno/api/stocks/fromtodate?fromdate=2018-01-01&todate=2018-02-01  {"stockSymbols":["GOOG","TSLA"]}
13.	POST   http://ipaddress:portno/api/stocks/frominterval?fromdate=2018-01-01&interval=MONTHLY (DAILY, WEEKLY)  {"stockSymbols":["GOOG","TSLA"]}
14.	POST   http://ipaddress:portno/api/stocks/fromtointerval?fromdate=2018-01-01&todate=2018-02-10&interval=DAILY  (WEEKLY, MONTHLY) {"stockSymbols":["GOOG","TSLA"]}
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

SINGLE  STOCK QUOTE DETAILS:--

15.	GET   http://ipaddress:portno/api/stock/quote/GOOG
16.	GET   http://ipaddress:portno/api/stock/quote/AAPL/historicalquote
17.	GET   http://ipaddress:portno/api/stock/quote/AAPL/historicalquote/fromdate?fromdate=2018-01-01
18.	GET   http://ipaddress:portno/api/stock/quote/AAPL/historicalquote/interval?interval=WEEKLY   (MONTHLY, DAILY)
19.	GET   http://ipaddress:portno/api/stock/quote/AAPL/historicalquote/fromtodate?fromdate=2017-01-30&todate=2018-01-01
20.	GET   http://ipaddress:portno/api/stock/quote/AAPL/historicalquote/fromdate/interval?fromdate=2017-01-01&interval=DAILY  (WEEKLY, MONTHLY)
21.	GET   http://ipaddress:portno/api/stock/quote/AAPL/historicalquote/fromtodate/interval?fromdate=2017-01-02&todate=2017-04-01&interval=MONTHLY (DAILY, WEEKLY)
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

MULTIPLE STOCK QUOTE DETAILS:--

22.	POST  http://ipaddress:portno/api/stock/quotes  {"stockQuoteSymbols":["GOOG"]}
23.	POST  http://ipaddress:portno/api/stock/quotes/historicalquotes {"stockQuoteSymbols":["AAPL"]}
24.	POST  http://ipaddress:portno/api/stock/quotes/historicalquotes/fromdate?fromdate=2018-01-01   {"stockQuoteSymbols":["AAPL","GOOG"]}
25.	POST  http://ipaddress:portno/api/stock/quotes/historicalquotes/interval?interval=WEEKLY  (WEEKLY, MONTHLY, DAILY)
26.	POST  http://ipaddress:portno/api/stock/quotes/historicalquotes/fromtodate?fromdate=2017-02-10&todate=2018-08-10  {"stockQuoteSymbols":["AAPL","GOOG"]}
27.	POST  http://ipaddress:portno/api/stock/quotes/historicalquotes/fromdate/interval?fromdate=2017-02-10&interval=MONTHLY  {"stockQuoteSymbols":["AAPL","GOOG"]} (WEEKLY, MONTHLY, DAILY)
28.	POST  http://ipaddress:portno/api/stock/quotes/historicalquotes/fromtodate/interval?fromdate=2017-02-10&todate=2018-01-01&interval=weekly  {"stockQuoteSymbols":["AAPL","GOOG"]}  (WEEKLY, MONTHLY, DAILY)
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

STOCK FX DETAILS:--

29.	GET   http://ipaddress:portno/api/stock/fxsymbols
30.	GET   http://ipaddress:portno/api/stock/fxquote/USDJPY=X
----------------------------------------------------------------------------------------------------------------------------------------

MULTIPLE STOCK FX DETAILS:--

31.	POST  http://ipaddress:portno/api/stock/fxquotes   {"fxSymbols":["NZDAUD=X","USDJPY=X","USDNZD=X","HKDUSD=X","CHFHKD=X"]}
----------------------------------------------------------------------------------------------------------------------------------------

STOCK PRICE DETAILS:--

32.	GET   http://ipaddress:portno/api/stock/price/INTC
----------------------------------------------------------------------------------------------------------------------------------------

MULTIPLE STOCK PRICE DETAILS:--

33.	POST http://ipaddress:portno/api/stock/prices  {"stockSymbols":["GOOG","TSLA","INTC"]}
----------------------------------------------------------------------------------------------------------------------------------------

STOCK DIVIDEND DETAILS:--

34.	GET  http://ipaddress:portno/api/stock/dividend/INTC
35.	GET  http://ipaddress:portno/api/stock/dividend/history/AAPL
36.	GET  http://ipaddress:portno/api/stock/dividend/historyfromdate/AAPL?fromdate=2018-04-01
37.	GET  http://ipaddress:portno/api/stock/dividend/historyfromtodate/AAPL?fromdate=2018-08-01&todate=2018-08-11
----------------------------------------------------------------------------------------------------------------------------------------

MULTIPLE STOCK DIVIDEND DETAILS:--

38.	POST  http://ipaddress:portno/api/stock/dividends  {"stockSymbols":["AAPL","INTC"]}
39.	POST  http://ipaddress:portno/api/stock/dividends/history  {"stockSymbols":["AAPL","INTC"]}
40.	POST  http://ipaddress:portno/api/stock/dividends/historyfromdate?fromdate=2016-01-01  {"stockSymbols":["AAPL","INTC"]}
41.	POST  http://ipaddress:portno/api/stock/dividends/historyfromtodate?fromdate=2016-01-01&todate=2018-01-01   {"stockSymbols":["AAPL","INTC"]}
---------------------------------------------------------------------------------------------------------------------------------------------------

STOCK SEARCH QUERY:--

42.	GET  http://ipaddress:portno/api/stocksearch?query=DELL
---------------------------------------------------------------------------------------------------------------------------------------------------

STOCK NEWS DETAIL:--

43.	GET http://ipaddress:portno/api/stocknew/YHOO
---------------------------------------------------------------------------------------------------------------------------------------------------

MULTIPLE STOCK NEWS DETAILS:--

44.	POST http://ipaddress:portno/api/stocknews  {"stockSymbols":["DELL","GOOG","YHOO"]}
---------------------------------------------------------------------------------------------------------------------------------------------------

STOCK STATISTIC DETAILS:--

45.	GET  http://ipaddress:portno/api/stock/statistic/TSLA
---------------------------------------------------------------------------------------------------------------------------------------------------

MULTIPLE STOCK STATISTICS DETAILS:--

46.	POST  http://ipaddress:portno/api/stock/statistics  {"stockSymbols":["AAPL","GOOG","TSLA"]}


