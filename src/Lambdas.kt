typealias  DoubleConversion = (Double) -> Double

fun convert(x: Double, converter: DoubleConversion) : Double {
    val result = converter(x)
    println("$x is converted to $result")
    return result
}

fun converFive(converter: (Int) -> Double) : Double {
    val result =  converter(5)
    println("5 is converted to $result")
    return result
}

fun getConversionLambda (str: String) : DoubleConversion {
    if (str == "CentigradeToFahrenheit") {
        return {it * 1.8 + 32}
    } else if (str == "KgsToPounds") {
        return { it * 2.204623}
    } else if (str == "PoundToUsTons") {
        return { it / 2000.0}
    } else {
        return {it}
    }
}

fun combine (lambda1: DoubleConversion, lambda2: DoubleConversion) : DoubleConversion {
    return {x: Double -> lambda2(lambda1(x))}
}

fun main(args: Array<String>) {
    var addfive = {x: Int -> x + 5}
    println("pass 6 to addfive: ${addfive(6)}" )

    val addInts = {x: Int, y: Int -> x + y}
    val result =  addInts.invoke(6, 7)
    println("Pass 6, 7 to addInts: $result")

    val intLambda: (Int, Int) -> Int = {x, y -> x * y}
    println("Pass 10, 11 to intLambda: ${intLambda(10, 11)}")

     val addseven: (Int) -> Int = {it + 7}
    println("Pass 12 to addSeven: ${addseven(12)}")

    val myLambda: () -> Unit = {println("Hi")}
    myLambda()

 //   convert(20.0) { it * 1.8 + 32}
   // converFive { it * 1.8 + 32 }

    // convert 2.5kg to Pound
    println("convert 2.5kg to Pounds: ${getConversionLambda("KgsToPounds")(2.5)}")

    //define two conversion lambdas

    val kgsToPoundsLambda = getConversionLambda("KgsToPounds")
    val poundsToUsTonnslambda = getConversionLambda("PoundsToUsTons")

    //combine the two lambdas to create a new one
    val kgsToUsTonsLambda = combine(kgsToPoundsLambda, poundsToUsTonnslambda)

    //use the new lambda to convert 17.4 to US tons
    val value = 17.4
    println("$value kgs is ${convert(value, kgsToUsTonsLambda)} US tons")
}