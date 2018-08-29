
from sklearn.decomposition import PCA
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

#read file
data =pd.read_csv('MOBSOS_INVOCATION.csv')

#select attributes to transfer
datacol=data[['SCODE','MNAME']]
#datacol.to_csv("test.csv",index=False,sep=',')

#encoding data
onehot=pd.get_dummies(datacol,columns=datacol.columns)
#onehot.to_csv("onehotencoding.csv",index=False,sep=',')

#using pca to decrease dimension
pca = PCA(n_components=1)
principalComponents = pca.fit_transform(onehot)
principalDf = pd.DataFrame(data = principalComponents
             , columns = ['principal component 1'])
#principalDf.to_csv("oneDimension.csv",index=False,sep=',')

#add time attributes to principalDf
time=data[['STIME','ETIME']]
#time.to_csv("test.csv",index=False,sep=',')
oneDimensionWithTime= pd.concat([principalDf,time],axis=1)
#oneDimensionWithTime.to_csv("oneDimensionWithTime.csv",index=False,sep=',')

'''
给序列标号
label=['num']
for i in range(345682):
   label.append(i)
label=pd.DataFrame(label)
label.to_csv("label.csv",index=False,sep=',',header=1)
'''
label=pd.read_csv('label.csv')
oneDimensionWithLabel=pd.concat([principalDf,label],axis=1)
oneDimensionWithLabel.to_csv('oneDimensionWithLabel.csv',index=False,sep=',')