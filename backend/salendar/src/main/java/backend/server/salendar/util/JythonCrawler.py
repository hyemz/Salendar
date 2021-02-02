from customLib.selenium.webdriver import *

driver = webdriver.chrome('./customLib/chromedriver')  # Optional argument, if not specified will search path.
driver.get('https://www.lotteon.com/display/plan/planDetail/12710?mall_no=7');

driver.find_elements_by_class_name('imgOut')
html = driver.page_source

driver.quit()


#
# category = soup.select(
#     'bls-winners-list > ul > li > div.winners-list__info > a'
# )
# movie = soup.select(
#     'bls-winners-list > ul > li > div.winners-list__info > h3 > a'
# )
# producer = soup.select(
#     'bls-winners-list > ul > li > div.winners-list__info > p'
# )
#
# oscars_2020 = []
# for item in zip(category, movie, producer):
#     oscars_2020.append(
#         {
#             'category' : item[0].text,
#             'movie'    : item[1].text,
#             'producer' : item[2].text
#         }
#     )
#
# data = pd.DataFrame(oscars_2020)
# print(data)
# data.to_csv('oscars_2020.csv')