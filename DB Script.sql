create table login(
nameID varchar(50),
phoneID numeric(10,0),
day numeric(1,0),
loginflag numeric(1,0)
constraint login_pk Primary Key(phoneID));


create table company (
company_name varchar(50),
r1_price numeric(6,2),
r2_price numeric(6,2),
r3_price numeric(6,2),
r4_price numeric(6,2),
r5_price numeric(6,2)
constraint company_pk Primary Key(company_name));


create table valuation(
phoneID numeric(10,0),
cash numeric(8,2),
A_shares numeric(6,0),
B_shares numeric(6,0),
C_shares numeric(6,0),
D_shares numeric(6,0),
E_shares numeric(6,0),
F_shares numeric(6,0),
G_shares numeric(6,0),
H_shares numeric(6,0),
constraint valuation_fk Foreign Key(phoneID) references login(phoneID) on update cascade);


create table trade(
phoneID numeric(10,0),
company_name varchar(50),
round_no numeric(1,0),
buy numeric(8,0),
sell numeric(8,0),
constraint trade_fk1 Foreign Key(phoneID) references login(phoneID) on update cascade,
constraint trade_fk2 Foreign Key(company_name) references company(company_name) on update cascade);


create table powercard(
phoneID numeric(10,0),
pc2 numeric(1,0),
pc3 numeric(1,0),
constraint powercard_fk Foreign Key(phoneID) references login(phoneID) on update cascade);

create table rounds(r1 numeric(1,0),r2 numeric(1,0),r3 numeric(1,0),r4 numeric(1,0),r5 numeric(1,0));
insert into rounds values(1,1,1,1,1);




create trigger powercard_insert on login for insert
as
begin
insert into powercard values((select phoneID from inserted),1,1);
end;
go


create trigger valuation_insert on login for insert
as
begin
insert into valuation values((select phoneID from inserted),100000,0,0,0,0,0,0,0,0);
end;
go






insert into login values('Malhar',9004044310);


insert into company(company_name,r1_price) values('A',100);
insert into company(company_name,r1_price) values('B',100);
insert into company(company_name,r1_price) values('C',100);
insert into company(company_name,r1_price) values('D',100);
insert into company(company_name,r1_price) values('E',100);
insert into company(company_name,r1_price) values('F',100);
insert into company(company_name,r1_price) values('G',100);
insert into company(company_name,r1_price) values('H',100);


--UPDATE PRICES AFTER EACH ROUND
select company_name,sum(buy)-sum(sell) as change from trade where round_no=1 group by company_name ;
update company set r2_price=r1_price+0.2*70 where company_name='A';

--SET FLAGS FOR NEXT ROUND
update rounds set r2=1,r1=0;
