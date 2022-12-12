# FCFS

AT sort
loop i
loop j=i+1
if(AT[i]<AT[j]){
swap -> AT,BT,PID;
}

CT logic
CT[0] = at[0] + BT[0]
loop from 1
if AT[i] >CT[i-1]
CT[i] = AT[i] + Bt[i]
else
CT[i]= CT[i-1] + BT[i]

calculate
tat = ct-at
wt = tat-bt
avgtat = tat/process
avgwt = wt/process

# SJF

SJF

AT sort
loop i
loop j=i+1
if(AT[i]<AT[j]){
swap -> AT,BT,PID;
}

CT logic
int tot = 0; // total time -> to break the while
int time = 0;
int c = n; ->why n because if it

while(true)
if(tot == n)
break;
loop
if(AT[i] <= time) && (f[i] == 0) && (BT[i]<min))
min = BT[i]; -> get min BT
c=i; -> and index of it
if(time is not less than and equal to AT[i])
time++;
else
CT[c] = time+BT[3];
time+=BT[c];
F[c] = 1; -> flag
tot++; ->to end loop inc

calculate
tat = ct-at
wt = tat-bt
avgtat = tat/process
avgwt = wt/process

# SRTF

SRTF

AT sort
loop i
loop j=i+1
if(AT[i]<AT[j]){
swap -> AT,BT,PID;
}

CT logic
int tot = 0; // total time -> to break the while
int time = 0;
int c = n; ->why n because if it

while(true)
if(tot == n)
break;
loop
if(AT[i] <= time) && (f[i] == 0) && (BT[i]<min))
min = BT[i]; -> get min BT
c=i; -> and index of it
if(time is not less than and equal to AT[i])
time++;
else
BT[c]--; -> dec by 1 every time
time++; -> inc time by 1
if(BT[c] == 0)
CT[c] = time;
F[c] = 1; -> flag
tot++; ->to end loop inc

calculate
tat = ct-at
wt = tat-bt
avgtat = tat/process
avgwt = wt/process

# Round Robin

![image](https://user-images.githubusercontent.com/81668653/206990189-b355acbc-f1db-4573-9298-1ac0d6fc5bdc.png)

CT logic
int temp;
int time = 0;

while (true) {
for (i = 0, count = 0; i < n; i++) {
temp = qt;
if (rem_bt[i] == 0) {
count++;
continue;
}
if (rem_bt[i] > qt)
rem_bt[i] = rem_bt[i] - qt;
else if (rem_bt[i] >= 0) {
temp = rem_bt[i];
rem_bt[i] = 0;
}
sq = sq + temp;
tat[i] = sq;
}
if (n == count)
break;
}
here TAT is euqal to CT
calculate
wt = tat-bt
avgtat = tat/process
avgwt = wt/process
