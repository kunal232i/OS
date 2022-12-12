echo "Enter the string 1"
read str1
len1=${#str1}
echo "Enter the string 2"
read str2
len2=${#str2}

if [ $len1 = $len2 ]
then
        echo "$str1 & $str2 lengths are equal"
elif [ $len1 -gt $len2 ]
then
        echo "$str1 length is greater than $str2"
else
        echo "$str2 length is greater than $str1"
fi

if [ $str1 == $str2 ]
then
        echo "$str1 & $str2 are equal"
else
        echo "Strings are not equal"
fi