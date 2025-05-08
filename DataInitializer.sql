BEGIN;

-- 0. Kích hoạt pgcrypto để dùng crypt()/gen_salt()
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- 1. Thêm hai học kỳ nếu chưa có
INSERT INTO semester (code, year, short_description)
SELECT 2431, 2024, 'Học kỳ 1 năm 2024'
WHERE NOT EXISTS (SELECT 1 FROM semester WHERE code = 2431);

INSERT INTO semester (code, year, short_description)
SELECT 2333, 2023, 'Học kỳ 2 của 2023'
WHERE NOT EXISTS (SELECT 1 FROM semester WHERE code = 2333);

-- 2. Thêm 3 sinh viên mẫu nếu chưa có (mã hoá mật khẩu)
-- INSERT INTO students (code, full_name, user_name, password)
-- SELECT 
--   v.code,
--   v.full_name,
--   v.user_name,
--   crypt(v.plain_pwd, gen_salt('bf'))
-- FROM (VALUES
--   (1, 'Admin', 'admin', '123456'),
--   (2, 'User', 'user', '123456'),
--   (22002575, 'Trần Gia Nguyên Phong', 'phong.tgn02575', '123456'),
--   (22002581, 'Võ Thị Kim Ngân',       'ngan.vtk02581',   '123456'),
--   (22002576, 'Nguyễn Văn An',          'an.nv02576',      '123456')
-- ) AS v(code, full_name, user_name, plain_pwd)
-- WHERE NOT EXISTS (SELECT 1 FROM students WHERE code = v.code);

-- 3a. Chèn 12 khóa mẫu cho kỳ 2431
WITH base_courses(code, name, credit, price) AS (
  VALUES
    ('KHTQ 105DV01','Toán Rời rạc',                                3, 4383000),
    ('PSY 107DV01','Tâm lý học - Khái niệm và UD',                 3, 4464000),
    ('DC 140DV01', 'Triết học Mác-Lênin',                          3, 3465000),
    ('CN 103DV01', 'Mạng máy tính cơ sở',                         3, 5653000),
    ('CN 104DV01', 'Hệ thống Máy tính',                           3, 5653000),
    ('DC 144DV01', 'Lịch sử Đảng Cộng sản Việt Nam',               3, 2310000),
    ('SW 103DV01', 'Lập trình Hướng đối tượng',                   3, 5653000),
    ('SW 402DE01', 'Kiến trúc Phần mềm',                         3, 8260000),
    ('PHI 117DV01','Triết học trong Cuộc sống',                   3, 4464000),
    ('SW 210DE01', 'Công nghệ Phần mềm',                         3, 6883000),
    ('SW 306DV01', 'Phát triển Web Front-End',                   3, 5653000),
    ('SW 310DV01', 'Phát triển ứng dụng trên TB di động',        3, 6218000)
)
INSERT INTO courses (code, name, credit, price, semester_id)
SELECT
  bc.code,
  bc.name,
  bc.credit,
  bc.price,
  (SELECT id FROM semester WHERE code = 2431)
FROM base_courses bc
WHERE NOT EXISTS (
  SELECT 1 FROM courses c
  WHERE c.code = bc.code
    AND c.student_id IS NULL
    AND c.semester_id = (SELECT id FROM semester WHERE code = 2431)
);

-- 3b. Chèn 12 khóa mẫu cho kỳ 2333
WITH base_courses(code, name, credit, price) AS (
  VALUES
    ('KHTQ 105DV01','Toán Rời rạc',                                3, 4383000),
    ('PSY 107DV01','Tâm lý học - Khái niệm và UD',                 3, 4464000),
    ('DC 140DV01', 'Triết học Mác-Lênin',                          3, 3465000),
    ('CN 103DV01', 'Mạng máy tính cơ sở',                         3, 5653000),
    ('CN 104DV01', 'Hệ thống Máy tính',                           3, 5653000),
    ('DC 144DV01', 'Lịch sử Đảng Cộng sản Việt Nam',               3, 2310000),
    ('SW 103DV01', 'Lập trình Hướng đối tượng',                   3, 5653000),
    ('SW 402DE01', 'Kiến trúc Phần mềm',                         3, 8260000),
    ('PHI 117DV01','Triết học trong Cuộc sống',                   3, 4464000),
    ('SW 210DE01', 'Công nghệ Phần mềm',                         3, 6883000),
    ('SW 306DV01', 'Phát triển Web Front-End',                   3, 5653000),
    ('SW 310DV01', 'Phát triển ứng dụng trên TB di động',        3, 6218000)
)
INSERT INTO courses (code, name, credit, price, semester_id)
SELECT
  bc.code,
  bc.name,
  bc.credit,
  bc.price,
  (SELECT id FROM semester WHERE code = 2333)
FROM base_courses bc
WHERE NOT EXISTS (
  SELECT 1 FROM courses c
  WHERE c.code = bc.code
    AND c.student_id IS NULL
    AND c.semester_id = (SELECT id FROM semester WHERE code = 2333)
);

-- 4. Enroll ngẫu nhiên 3–4 khóa cho mỗi sinh viên ở kỳ 2431
WITH templates2431 AS (
  SELECT id, code, name, credit, price, semester_id
  FROM courses
  WHERE student_id IS NULL
    AND semester_id = (SELECT id FROM semester WHERE code = 2431)
), to_enroll2431 AS (
  SELECT
    s.id   AS student_id,
    t.id   AS template_id
  FROM students s
  CROSS JOIN LATERAL (
    SELECT id
    FROM templates2431
    ORDER BY random()
    LIMIT (floor(random()*2) + 3)::int
  ) t
  WHERE s.code IN (22002575, 22002581, 22002576)
)
INSERT INTO courses (code, name, credit, price, semester_id, student_id)
SELECT
  tpl.code,
  tpl.name,
  tpl.credit,
  tpl.price,
  tpl.semester_id,
  e.student_id
FROM to_enroll2431 e
JOIN templates2431 tpl ON tpl.id = e.template_id;

-- 5. Enroll ngẫu nhiên 3–4 khóa cho mỗi sinh viên ở kỳ 2333
WITH templates2333 AS (
  SELECT id, code, name, credit, price, semester_id
  FROM courses
  WHERE student_id IS NULL
    AND semester_id = (SELECT id FROM semester WHERE code = 2333)
), to_enroll2333 AS (
  SELECT
    s.id   AS student_id,
    t.id   AS template_id
  FROM students s
  CROSS JOIN LATERAL (
    SELECT id
    FROM templates2333
    ORDER BY random()
    LIMIT (floor(random()*2) + 3)::int
  ) t
  WHERE s.code IN (22002575, 22002581, 22002576)
)
INSERT INTO courses (code, name, credit, price, semester_id, student_id)
SELECT
  tpl.code,
  tpl.name,
  tpl.credit,
  tpl.price,
  tpl.semester_id,
  e.student_id
FROM to_enroll2333 e
JOIN templates2333 tpl ON tpl.id = e.template_id;

-- 6. Thêm điểm ngẫu nhiên từ 5.0 trở lên (1 chữ số thập phân) cho mọi enrollment ở cả hai kỳ
UPDATE courses
SET grade = round((random() * 5 + 5)::numeric, 1)
WHERE student_id IS NOT NULL
  AND semester_id IN (
    (SELECT id FROM semester WHERE code = 2431),
    (SELECT id FROM semester WHERE code = 2333)
  );

-- 7. Kiểm tra kết quả
SELECT
  s.code        AS student_code,
  s.full_name   AS student_name,
  sem.code      AS semester_code,
  c.code        AS course_code,
  c.grade
FROM courses c
JOIN students s  ON c.student_id  = s.id
JOIN semester sem ON c.semester_id = sem.id
ORDER BY s.code, c.code;

-- 8. Gán ngày & giờ thi ngẫu nhiên cho các enrollment của kỳ 2431
UPDATE courses
SET
  final_exam_date = date '2024-05-20' 
                    + (floor(random()*6)::int) * interval '1 day',
  final_exam_time = time '08:00'
                    + (floor(random()*4)::int) * interval '2 hour'
WHERE student_id IS NOT NULL
  AND semester_id = (SELECT id FROM semester WHERE code = 2431);

-- 9. Gán ngày & giờ thi ngẫu nhiên cho các enrollment của kỳ 2333
UPDATE courses
SET
  final_exam_date = date '2023-12-10'
                    + (floor(random()*6)::int) * interval '1 day',
  final_exam_time = time '09:00'
                    + (floor(random()*3)::int) * interval '3 hour'
WHERE student_id IS NOT NULL
  AND semester_id = (SELECT id FROM semester WHERE code = 2333);

-- 10. Kiểm tra kết quả
SELECT
  s.code             AS student_code,
  s.full_name        AS student_name,
  sem.code           AS semester_code,
  c.code             AS course_code,
  c.grade            AS course_grade,
  c.final_exam_date  AS exam_date,
  c.final_exam_time  AS exam_time
FROM courses c
JOIN students s  ON c.student_id  = s.id
JOIN semester sem ON c.semester_id = sem.id
WHERE c.student_id IS NOT NULL
  AND sem.code IN (2431, 2333)
ORDER BY sem.code, s.code, c.code;

-- 11. Tính và chèn học phí cho mỗi sinh viên mỗi kỳ (nếu chưa có)
WITH tuition_calc AS (
  SELECT
    c.student_id,
    c.semester_id,
    SUM(c.price) AS total_amount
  FROM courses c
  WHERE c.student_id IS NOT NULL
  GROUP BY c.student_id, c.semester_id
)
INSERT INTO tuition (
  student_id,
  semester_id,
  total,
  paid,
  refund,
  balance,
  is_paid
)
SELECT
  tc.student_id,
  tc.semester_id,
  tc.total_amount,          -- tổng học phí
  tc.total_amount AS paid,  -- đã đóng đủ
  0            AS refund,   -- chưa có khoản hoàn
  0            AS balance,  -- còn nợ = 0
  TRUE         AS is_paid   -- đã thanh toán
FROM tuition_calc tc
WHERE NOT EXISTS (
  SELECT 1
    FROM tuition t
   WHERE t.student_id  = tc.student_id
     AND t.semester_id = tc.semester_id
);

-- 12. Kiểm tra kết quả
SELECT
  s.code           AS student_code,
  s.full_name      AS student_name,
  sem.code         AS semester_code,
  t.total          AS total_tuition,
  t.paid           AS amount_paid,
  t.refund         AS amount_refunded,
  t.balance        AS remaining_balance,
  t.is_paid        AS fully_paid
FROM tuition t
JOIN students s  ON t.student_id  = s.id
JOIN semester sem ON t.semester_id = sem.id
ORDER BY sem.code, s.code;

COMMIT;

