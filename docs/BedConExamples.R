# replace unknown values with 0
# Carrots  Grass  Pork	Beef	Corn	Fish
Rabbit   <- c(10,  7, 1,  2, NA,  1)
Cow      <- c( 7, 10, NA, NA, NA, NA)
Dog      <- c(NA,  1, 10, 10, NA, NA)
Pig      <- c( 5,  6,  4, NA,  7,  3)
Chicken  <- c( 7,  6,  2, NA, 10, NA)
Pinguin  <- c( 2,  2, NA,  2,  2, 10)
Bear     <- c( 2, NA,  8,  8,  2,  7)
Lion     <- c(NA, NA,  9, 10,  2, NA)
Tiger    <- c(NA, NA,  8, NA, NA,  5)
Antilope <- c( 6, 10,  1,  1, NA, NA)
Wolf     <- c( 1, NA, NA,  8, NA,  4)
Sheep    <- c(NA,  8, NA, NA, NA,  2)
# all the animals
animals <- c("Rabbit","Cow","Dog","Pig","Chicken","Pinguin","Bear","Lion","Tiger","Antilope","Wolf","Sheep")
# all the foods
foods <- c("Carrots","Grass","Pork", "Beef", "Corn", "Fish")
matrixRowAndColNames <- list(animals, foods)
# create a matrix from the ratings
animal2foodRatings <- matrix(
  data=c(Rabbit,Cow,Dog,Pig,Chicken,Pinguin,Bear,Lion,Tiger,Antilope,Wolf,Sheep),
  nrow=12,ncol=6,byrow=TRUE, matrixRowAndColNames)

animal2foodRatingsWithMean <- animal2foodRatings
animal2foodRatingsWithMean[is.na(animal2foodRatingsWithMean)] <- mean(rowMeans(animal2foodRatingsRecMatrix))

FactorStructure <- svd(animal2foodRatingsWithMean)
#
D <- diag(FactorStructure$d)
PredictedRatings <- FactorStructure$u %*% D %*% t(FactorStructure$v)
dimnames(PredictedRatings) <- matrixRowAndColNames

PredictiveMatrix <- matrix(nrow=length(animals), ncol=length(foods))
dimnames(PredictiveMatrix) <- matrixRowAndColNames
# Sheep Carrots prediction
k <- 2
for(animal in 1:length(animals)) {
    for(food in 1:length(foods)) {
        PredictiveMatrix[animal,food] <- (((FactorStructure$u[animal,1:k]*sqrt(FactorStructure$d[1:k]))%*%(sqrt(FactorStructure$d[1:k])*t(FactorStructure$v)[1:k,food]))[1,1])
    }
}
PredictiveMatrix
library(recommenderlab)

animal2foodRatingsRecMatrix <- as(animal2foodRatings, "realRatingMatrix")
animal2foodRatingsRecMatrix_n <- normalize(animal2foodRatingsRecMatrix)
animal2foodRatingsRecMatrix_n2 <- normalize(animal2foodRatingsRecMatrix, method="Z-score")

# Average user rating
mean(rowMeans(animal2foodRatingsRecMatrix))
# Average number of ratings per User
mean(rowCounts(animal2foodRatingsRecMatrix))
# Average number of ratings per Item
mean(colCounts(animal2foodRatingsRecMatrix))
# Amount of all ratings
length(getRatings(animal2foodRatingsRecMatrix))
# Histogram of ratings
hist(getRatings(animal2foodRatingsRecMatrix), breaks=10, main=paste("Distribution of Ratings"))

image(animal2foodRatingsRecMatrix, main="Raw Data")
image(animal2foodRatingsRecMatrix_n, main="Centered")
image(animal2foodRatingsRecMatrix_n2, main="Z-Score Normalization")

rec <- Recommender(animal2foodRatingsRecMatrix[1:10,], method = "IBCF")
recommenderRegistry$get_entry_names()