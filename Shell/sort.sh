echo "Enter the size of array"
read n
echo "Enter the array"
for((i=0;i<$n;i++))
do
    read b
    c[$i]=$b
done

for((j=0;j<$n;j++))
do
    for((m=$j+1;m<$n;m++))
    do
        if [ ${c[$j]} -gt ${c[$m]} ]
        then
            temp=${c[$j]}
            c[$j]=${c[$m]}
            c[$m]=$temp
        fi
    done
done

echo "Print sorted array"
echo ${c[*]}