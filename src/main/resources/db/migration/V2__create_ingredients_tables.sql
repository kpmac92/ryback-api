create table ingredients (
    id UUID default gen_random_uuid() primary key,
    name varchar(100),
    isDiscrete boolean
);

create table recipeIngredients (
    recipeId UUID references recipes(id),
    ingredientId UUID references ingredients(id),
    amountNumerator integer,
    amountDenominator integer,
    isPrimary boolean,
    primary key(recipeId, ingredientId)
)
