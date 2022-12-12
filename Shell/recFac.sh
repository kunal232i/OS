factorial_recursive()
{
  # If the number is less than or equal to 1, the factorial is 1
  if [[ $1 -le 1 ]]
  then
    factorial=1
  else
    # Recursively call the function with the number decremented by 1
    factorial_recursive $(($1 - 1))
    # Multiply the result of the previous recursion with the current number
    factorial=$(($factorial * $1))
  fi
}

echo "Enter Num : "
read n
factorial_recursive $n
echo $factorial