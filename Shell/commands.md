ls > outfile

# Create the rm alias

alias rm="rm -i"

# Use the rm alias to delete a file interactively

rm myfile

# currently logged-in user

w
w|wc -l

# all hidden files in "hidden" file

la- a > hidden

# check permission

ls -l filename

# permissions

chmod u+x,o-r filename

# Create a soft link to myfile named "mysoftlink"

ln -s myfile mysoftlink

# Create a hard link to myfile named "myhardlink"

ln -h myfile myhardlink
