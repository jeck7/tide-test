create table features
(
	id bigint auto_increment
		primary key,
	description varchar(255) not null
)
;

create table user_features
(
	user_id bigint not null,
	feature_id bigint not null,
	primary key (user_id, feature_id),
	constraint FKrbw20p8pmx70wfn155ygsy3ll
		foreign key (feature_id) references features (id)
)
;

create index FKrbw20p8pmx70wfn155ygsy3ll
	on user_features (feature_id)
;

create table users
(
	id bigint auto_increment
		primary key,
	created_at datetime not null,
	name varchar(255) not null,
	updated_at datetime not null
)
;

alter table user_features
	add constraint FKn1bl34rxkodll6hm0dmrdijx0
		foreign key (user_id) references users (id)
;

