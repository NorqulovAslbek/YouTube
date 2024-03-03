CREATE TRIGGER comment_like_trigger
    AFTER INSERT OR
UPDATE OR
DELETE
ON comment_like
    FOR EACH ROW
    EXECUTE FUNCTION comment_like_trigger_function();
