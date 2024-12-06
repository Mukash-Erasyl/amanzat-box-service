ALTER TABLE working_schedule
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

INSERT INTO working_schedule (opening_time_seconds, closing_time_seconds, working_days)
VALUES
    -- Круглосуточное
    (0, 86399, 'ALL'),

    -- Ежедневное с фиксированным временем
    (32400, 64800, 'Mon-Sun'),

    -- Рабочие дни (будни)
    (28800, 72000, 'Mon-Fri'),

    -- Работа по выходным
    (36000, 57600, 'Sat,Sun'),

    -- Ночные смены
    (79200, 21600, 'Mon-Sun'),

    -- Специальные дни (например, Рождество)
    (36000, 72000, 'Dec-25,Jan-01'),

    -- Летнее расписание (июнь–август)
    (32400, 72000, 'Jun-Aug');
