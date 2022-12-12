echo "Enter the Number till you want fibonacci series"
read n
a=0
b=1

echo "Fibonacci series is: "
for((i=0;i<$n;i++))
do
    echo -e $a
    fn=$((a+b))
    a=$b
    b=$fn
done