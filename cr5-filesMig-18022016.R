library("XML")
library("splitstackshape")
library("plyr")
library("stringr")
options(scipen = 999)
#Read the database XML
  doc <- xmlParse("C://Users//patri_000//Google Drive//Work//WHO//CanReg Database Processor Native//COR.xml")
#Registry name
  registry.name <- reg.name(doc)
#Parse the variables and dictionary nodes into a dataframe
  doc.data <- xmlToDataFrame(nodes = xmlChildren(xmlRoot(doc)[["variables"]]))
  dic.data <- xmlToDataFrame(nodes = xmlChildren(xmlRoot(doc)[["dictionaries"]]))

#Create the tables: Patient, Tumour and Source
  patient.data <- tables.cr5(doc.data,"Patient")
  tumour.data <- tables.cr5(doc.data,"Tumour")
  source.data <- tables.cr5(doc.data,"Source")

#Complete variable_length in the doc.data so we could put leading zeros in the variables that use dictionary data
  doc.data <- var.length(doc.data, dic.data)


#Import the MP file from CanReg4
  MP.data <- read.csv("cr4-pm.csv",stringsAsFactors = FALSE)
#The Patient table has a column that permits identify each patient in an unique way,
#but sometimes this variable is not available, so the best way is to use de PMCod that permits
#to match each MP

#We have to check if the data in each MP Patient is the same
#The user has to say which variables have to be compared to know if the patient that has
# a MP has the same data in each CanReg4 case
#One of the major problem during the CR4 migration wizard was that CR5 does not know which
#data is updated and which data is not updated. Here we are going to tell the user if there
#are differences between the data in each case

  error.MP.cases <- mp.error(MP.data, 
                             "Modif",#update date
                             c("ApePat", #last name
                               "ApeMat", #mother's maiden name
                               "Nombres", #first name
                               "Sexo",    #sex
                               "NoDoc",   #person id
                               "FecNac",  #DOB
                               "EstVit",  #Vital status
                               "FUC"),    #Last contact date
                             "PMCod") # multiple primary code


#Replace the Registry Number so each PM will have the same Registry Number
  #Caso: Registry Number
  #PMCod: multiple primary code
  #PMCod: multiple primary total
  MP.data[,c("Caso")] <- expanded.cases(MP.data,"PMCod","PMTot","Caso")[,c("Caso")]
  
  
#Table of PMTot to check how many patients and tumours have to be added
  MPTot.data <- as.data.frame.table(table(MP.data$PMTot),stringsAsFactors = FALSE)
  names(MPTot.data)[1] <- "PMTot"
  MPTot.data$PMTot <- as.numeric(MPTot.data$PMTot)
  
#How many patient do we have to add into the database? 
  total.patients <- sum(MPTot.data$Freq/MPTot.data$PMTot)

#Complete the data.frame for patient
  patient.data <- patient(patient.data, MP.data, "Modif","PMCod","Caso")

#Complete the data.frame for tumours
  #PMSeq: multiple primary sequence
  tumour.data <- tumour(tumour.data, MP.data, "Modif","PMSeq","Caso")

#Complete the data.frame for sources
#We have to ask to the user to define the variables for the Source table, sometimes they could have 
#several columns for several sources. We will show the variables in the source table and the user
#will say which variable corresponds to the variables in the source table.
#Note: the variables TumourIDSourceTable and SourceRecordID are not going to be shown

#Source table variables but TumourIDSourceTable and SourceRecordID are going to be excluded
  source.variables <- subset(names(source.data),!(names(source.data) %in% c("TumourIDSourceTable","SourceRecordID")))
  source.variables.full <- doc.data$full_name[doc.data$short_name %in% source.variables]
  source.data <- sources(source.data, MP.data, 2, 
                         c(1,"Inst1","Efec","Notif","HC1","Prof",2,"Inst2","Efec","Notif","HC2","Prof"),
                         c(1,"Inst","Efec","Notif","HC","Prof",2,"Inst","Efec","Notif","HC","Prof"),
                         "PMSeq","Caso")

#Variables with Dictionaries
  patient.dic <- doc.data$short_name[doc.data$variable_type=="Dict" & doc.data$table=="Patient"]
  source.dic <- doc.data$short_name[doc.data$variable_type=="Dict" & doc.data$table=="Source"]
  tumour.dic <- doc.data$short_name[doc.data$variable_type=="Dict" & doc.data$table=="Tumour"]

#Complete the variables with leading zeros
  patient.data <- leading.zeros(patient.data, doc.data, patient.dic)
  source.data <- leading.zeros(source.data, doc.data, source.dic)
  tumour.data <- leading.zeros(tumour.data, doc.data, tumour.dic)


  
  
#check if the dataframes patient and tumour have the correct number of cases
  if (total.patients==nrow(patient.data) & nrow(MP.data)==nrow(tumour.data)){
    print (paste("The data for patient, tumour and source is going to be save in: ",getwd(),sep=""))
    #Write the csv files that are going to be imported
    write.csv(tumour.data, file = "tumour.csv", row.names = FALSE)
    write.csv(patient.data, file = "patient.csv", row.names = FALSE)
    write.csv(source.data, file = "source.csv", row.names = FALSE)
  }else{
    print ("Error saving the files")
  }

  
  
  







