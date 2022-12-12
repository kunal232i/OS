echo "Enter num : "
read n
product=1
for((i=1;i<=$n;i++))
do
    product=$((product*i))
done
echo $product