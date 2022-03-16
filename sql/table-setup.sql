-- public.dguilds definition

-- Drop table

-- DROP TABLE dguilds;

CREATE schema if not exists bot;

CREATE TABLE bot.dguilds (
	id int8 NOT NULL,
	"name" text NOT NULL,
	is_whitelist bool NULL DEFAULT true,
	prefix text NULL DEFAULT '$'::text,
	enable_log bool NULL DEFAULT false,
	log_channel int8 NOT NULL DEFAULT 0,
	enable_welcome bool NULL DEFAULT false,
	welcome_channel int8 NOT NULL DEFAULT 0,
	welcome_text text NOT NULL DEFAULT ''::text,
	CONSTRAINT dguilds_pkey PRIMARY KEY (id)
);


-- public.dnotify definition

-- Drop table

-- DROP TABLE dnotify;

CREATE TABLE bot.dnotify (
	id serial NOT NULL,
	user_id int8 NOT NULL,
	awake_time timestamp NOT NULL,
	message text NOT NULL,
	CONSTRAINT dnotify_pkey PRIMARY KEY (id)
);


-- public.dusers definition

-- Drop table

-- DROP TABLE dusers;

CREATE TABLE bot.dusers (
	id int8 NOT NULL,
	"name" text NOT NULL,
	is_whitelist bool NULL DEFAULT true,
	"money" int8 NULL DEFAULT 0,
	last_daily timestamp NULL,
	streak_daily int4 NULL DEFAULT 0,
	world int4 NULL DEFAULT 0,
	last_move timestamp NULL,
	CONSTRAINT dusers_pkey PRIMARY KEY (id)
);


-- public.items definition

-- Drop table

-- DROP TABLE items;

CREATE TABLE bot.items (
	id text NOT NULL,
	emoji text NOT NULL,
	inner_sort int4 NULL,
	"name" text NOT NULL,
	rarity text NOT NULL,
	buy_price int4 NULL,
	sell_price int4 NULL,
	durability int4 NULL,
	description text NOT NULL,
	CONSTRAINT items_emoji_key UNIQUE (emoji),
	CONSTRAINT items_pkey PRIMARY KEY (id)
);


-- public.dusers_dguilds definition

-- Drop table

-- DROP TABLE dusers_dguilds;

CREATE TABLE bot.dusers_dguilds (
	user_id int8 NOT NULL,
	guild_id int8 NOT NULL,
	tempmute_end timestamp NULL,
	CONSTRAINT dusers_dguilds_pkey PRIMARY KEY (user_id, guild_id),
	CONSTRAINT dusers_dguilds_guild_id_fkey FOREIGN KEY (guild_id) REFERENCES bot.dguilds(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT dusers_dguilds_user_id_fkey FOREIGN KEY (user_id) REFERENCES bot.dusers(id) ON UPDATE CASCADE ON DELETE CASCADE
);


-- public.dusers_items definition

-- Drop table

-- DROP TABLE dusers_items;

CREATE TABLE bot.dusers_items (
	user_id int8 NOT NULL,
	item_id text NOT NULL,
	quantity int4 NULL DEFAULT 0,
	is_main bool NOT NULL DEFAULT false,
	durability_left int4 NULL,
	CONSTRAINT dusers_items_pkey PRIMARY KEY (user_id, item_id),
	CONSTRAINT dusers_items_item_id_fkey FOREIGN KEY (item_id) REFERENCES bot.items(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT dusers_items_user_id_fkey FOREIGN KEY (user_id) REFERENCES bot.dusers(id) ON UPDATE CASCADE ON DELETE CASCADE
);