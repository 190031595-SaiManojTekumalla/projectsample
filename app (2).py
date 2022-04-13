from datetime import date
from lib2to3.pgen2 import driver
import time
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By 
import csv


appNames = input().split()
AppData = []
driver = webdriver.Chrome(r"D:\PRISM\chromedriver.exe")
for kl in appNames:
    driver.get(f'https://play.google.com/store/search?q={kl}&c=apps')
    # seemore = driver.find_element(By.CLASS_NAME, 'W9yFB')
    # but = seemore.find_element(By.TAG_NAME,'a')
    # but.click()
    time.sleep(2)
    apps = driver.find_elements(By.CLASS_NAME, 'mpg5gc')
    applist = apps[:40]
    
    links = []
    for i in applist:
        ldiv = i.find_element(By.CLASS_NAME, 'Q9MA7b').find_element(By.TAG_NAME, 'a')
        singlelink = ldiv.get_attribute('href')
        links.append(singlelink)

    for i in links:
        driver.get(i)
        app = {}
        app['AppName'] = driver.find_element(By.CLASS_NAME, 'AHFaub').text
        app['Type'] = driver.find_elements(By.CLASS_NAME, 'R8zArc')[1].text
        try:
            app['Rating'] = driver.find_element(By.CLASS_NAME, 'BHMmbe').text
        except Exception as e:
            app['Rating'] = 'No Rating'
        try:
            app['Reviews'] = driver.find_element(By.CLASS_NAME, 'TBRnV').text
        except Exception as e:
            app['Reviews'] = 'No Reviews'
        try:
            app['Size'] = driver.find_elements(By.CLASS_NAME, 'hAyfc')[1].find_element(By.CLASS_NAME, 'htlgb').text
        except:
            app['Size'] = 'No Data'
        try:
            app['Installs'] = driver.find_elements(By.CLASS_NAME, 'hAyfc')[2].find_element(By.CLASS_NAME, 'htlgb').text
        except:
            app['Installs'] = 'No Data'
        AppData.append(app)


print(AppData)
driver.quit()
toCSV = AppData
header= list(AppData[0].keys())   
try:
    with open('output'+str(date.today())+'.csv',mode='w',encoding='utf8',newline='') as output_to_csv:
        dict_csv_writer = csv.DictWriter(output_to_csv, fieldnames=header,dialect='excel')
        dict_csv_writer.writeheader()
        dict_csv_writer.writerows(toCSV)
    print('\nData exported to csv succesfully and sample data')
except IOError as io:
    print('\n',io)
#instagram facebook mariokart