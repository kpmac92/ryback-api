create table ingredients (
    id UUID default gen_random_uuid() primary key,
    name varchar(100),
    discrete boolean
);

create table recipeIngredients (
    recipeId UUID references recipes(id),
    ingredientId UUID references ingredients(id),
    amountNumerator integer,
    amountDenominator integer,
    "primary" boolean,
    primary key(recipeId, ingredientId)
)
