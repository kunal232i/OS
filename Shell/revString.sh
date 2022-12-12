echo "Enter the string"
read str1
len=${#str1}
for((i=len-1;i>=0;i--))
do
    rev=$rev${str1:i:1}
    echo -n ${str1:i:1}
done
echo " "
echo $rev