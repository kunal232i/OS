echo "Enter the First String"
read str1
len=${#str1}
for((i=$len-1;i>=0;i--))
do
        rev=$rev${str1:$i:1}
done

if [ $str1 == $rev ]
then
        echo "String  is palindrome"
else
        echo "String is not palindrome"
fi