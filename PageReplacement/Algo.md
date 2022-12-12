## FIFO

FIFO(pages, frames)
queue = empty queue
faults = 0
for page in pages:
if page is not in queue:
if queue is full:
remove page at front of queue
add page to back of queue
faults = faults + 1
return faults
