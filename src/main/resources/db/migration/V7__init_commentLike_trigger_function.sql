CREATE OR REPLACE FUNCTION comment_like_trigger_function()
    RETURNS TRIGGER
    LANGUAGE PLPGSQL
AS $$
BEGIN
    -- Yangi video yoqtirilganda
    IF TG_OP = 'INSERT' then
        IF NEW.type = 'LIKE' then
UPDATE comment SET like_count = like_count + 1 WHERE id = NEW.comment_id;
ELSIF NEW.type = 'DISLIKE' then
UPDATE comment SET dislike_count = dislike_count + 1 WHERE id = NEW.comment_id;
END IF;
        -- comment o'zgartirilganda
    ELSIF TG_OP = 'UPDATE' then
        IF NEW.type = 'LIKE' AND OLD.type = 'DISLIKE' then
UPDATE comment SET like_count = like_count + 1, dislike_count = dislike_count - 1 WHERE id = NEW.comment_id;
ELSIF NEW.type = 'DISLIKE' AND OLD.type = 'LIKE' then
UPDATE comment SET like_count = like_count - 1, dislike_count = dislike_count + 1 WHERE id = NEW.comment_id;
END IF;
        -- comment o'chirilganda
    ELSIF TG_OP = 'DELETE' then
        IF OLD.type = 'LIKE' then
UPDATE comment SET like_count = like_count - 1 WHERE id = OLD.comment_id;
ELSIF OLD.type = 'DISLIKE' then
UPDATE comment SET dislike_count = dislike_count - 1 WHERE id = OLD.comment_id;
END IF;
END IF;
    -- Trigger funksiyasiga kelayotgan qatorda, old yoki new qiymatlarni qaytarish kerak
RETURN OLD;
END;
$$;
