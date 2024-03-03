CREATE TRIGGER video_like_trigger
    AFTER INSERT OR
UPDATE OR
DELETE
ON video_like
    FOR EACH ROW
    EXECUTE FUNCTION video_like_trigger_function();
