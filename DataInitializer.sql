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

-- Step 10: Initialize photo balance for each student (1–5 million VND) if null
UPDATE students
   SET photocopy_balance = (floor(random() * 5 + 1)::int) * 1000000
 WHERE photocopy_balance IS NULL
   AND code IN (22002575, 22002581, 22002576);

-- Step 11: Insert 5 random photocopy expense transactions per student
WITH studs AS (
  SELECT id FROM students
   WHERE code IN (22002575, 22002581, 22002576)
)
INSERT INTO photocopy_transactions (date, amount, student_id)
SELECT CURRENT_DATE - (floor(random() * 30)::int),
       (floor(random() * 5)::int + 1) * 100000,
       s.id
  FROM studs s
 CROSS JOIN generate_series(1,5);

-- Step 12: Insert an additional 10 random photocopy transactions per student
WITH studs AS (
  SELECT id FROM students
   WHERE code IN (22002575, 22002581, 22002576)
)
INSERT INTO photocopy_transactions (date, amount, student_id)
SELECT CURRENT_DATE - (floor(random() * 60)::int),
       (floor(random() * 18)::int + 1) * 50000,
       s.id
  FROM studs s
 CROSS JOIN generate_series(1,10);

-- Step 13: Recalculate photo balance = initial balance – total spent
UPDATE students st
   SET photocopy_balance = st.photocopy_balance - COALESCE(tx.total_spent, 0)
  FROM (
    SELECT student_id, SUM(amount) AS total_spent
      FROM photocopy_transactions
     GROUP BY student_id
  ) tx
 WHERE st.id = tx.student_id;

-- Step 14: Ensure no negative photo balances
UPDATE students
   SET photocopy_balance = 0
 WHERE photocopy_balance < 0;

-- Step 15: Final check of student balances and transaction counts
SELECT
  s.code                            AS student_code,
  s.full_name                       AS student_name,
  to_char(s.photocopy_balance,
          'FM9G999G999')           AS remaining_balance_vnd,
  tx.count_tx                       AS num_transactions,
  to_char(tx.total_spent,
          'FM9G999G999')           AS total_spent_vnd
FROM students s
LEFT JOIN (
  SELECT student_id,
         COUNT(*)    AS count_tx,
         SUM(amount) AS total_spent
    FROM photocopy_transactions
   GROUP BY student_id
) tx ON tx.student_id = s.id
WHERE s.code IN (22002575, 22002581, 22002576)
ORDER BY s.code;

-- Step 16: Insert announcements from provided JSON data
INSERT INTO announcements (title, link_url, image_link_url, category)
SELECT v.title, v.link_url, v.image_link_url, v.category
FROM (VALUES
  -- EVENTS
  (
    '[HSU EXPERIENCE DAY 2025] – THE TECH ERA – Khám phá nhóm ngành Công nghệ thông tin tại Trường Đại học Hoa Sen',
    'https://www.hoasen.edu.vn/event/the-tech-era-kham-pha-cong-nghe-thong-tin',
    'https://www.hoasen.edu.vn/event/wp-content/uploads/sites/9/2025/05/CNTT.png',
    'EVENT'
  ),
  (
    '[HSU EXPERIENCE DAY 2025] Ngày hội trải nghiệm ART – SPIRATION cùng Khoa Thiết kế – Nghệ thuật',
    'https://www.hoasen.edu.vn/event/buoc-chan-dau-tien-cung-the-guiding-star-mua-7',
    'https://www.hoasen.edu.vn/event/wp-content/uploads/sites/9/2025/05/TKNT.png',
    'EVENT'
  ),
  (
    'CLUBVERSE 2025: Đổ bộ vũ trụ CLB sôi động nhất năm tại HSU',
    'https://www.hoasen.edu.vn/event/clubverse-2025-do-bo-vu-tru-clb-soi-dong-nhat-nam-tai-hsu',
    'https://www.hoasen.edu.vn/event/wp-content/uploads/sites/9/2025/04/LEDThanhThai-1920x1080-01.png',
    'EVENT'
  ),
  (
    'I-HOTELIER:  Gần 2 thập kỹ gắn bó với cuộc thi chuyên ngành nhà hàng – khách sạn',
    'https://www.hoasen.edu.vn/event/i-hotelier-mua-xvii-2025',
    'https://www.hoasen.edu.vn/event/wp-content/uploads/sites/9/2025/05/493263580_1093629526124647_2119818882837534840_n.jpg',
    'EVENT'
  ),
  (
    'I-BARTENDER CONTEST: Nâng tầm thế hệ Bartender trẻ, góp phần phát triển bền vững ngành pha chế tại Việt Nam',
    'https://www.hoasen.edu.vn/event/i-bartender-contest-mua-6',
    'https://www.hoasen.edu.vn/event/wp-content/uploads/sites/9/2025/05/I-Bartender.png',
    'EVENT'
  ),
  (
    'Cuộc thi “Nhà thiết kế sáng tạo: Trang sức tương lai 2025”',
    'https://www.hoasen.edu.vn/event/nha-thiet-ke-sang-tao-trang-suc-tuong-lai-2025',
    'https://www.hoasen.edu.vn/event/wp-content/uploads/sites/9/2025/04/z6504122330036_f2c45af8e2dc6f933e6ee0129114945d.jpg',
    'EVENT'
  ),
  -- ANNOUNCEMENTS
  (
    '[2025_036] Thông báo về việc hướng dẫn sinh viên Khóa 2024 điều chỉnh kế hoạch học tập cá nhân và đăng ký học phần trên hệ thống',
    'https://file.hoasen.edu.vn/files/TB_huong_dan_lap_KHHT_CN_Khoa_2024.pdf',
    NULL,
    'ANNOUNCE'
  ),
  (
    '[2025_035] Danh sách Sinh viên dự thi Học phần EII 1, EII 2 GĐ 1 Học kỳ 2 _ Năm học 2024-2025',
    'https://file.hoasen.edu.vn/files/DSSV_thi_CK_Speaking_Written_EII1_2_HK24.2A.rar',
    NULL,
    'ANNOUNCE'
  ),
  (
    '[2025_034] Thông báo danh sách sv bị cấm thi lớp HP EII 1, 2 của HK 2 - 2433',
    'https://file.hoasen.edu.vn/files/TB_DSSV_CAM_THI_CK_HK_2_-_2433_-_HP_EII1,2.pdf',
    NULL,
    'ANNOUNCE'
  ),
  (
    '[2025_033] Lịch thi Cuối kỳ Học phần EII 3, EII 4 (Giai đoạn 1) Học kỳ 2_Năm học 2024-2025',
    'https://file.hoasen.edu.vn/files/Lich_thi_CK_EII_3_4_GD1_HK2433.rar',
    NULL,
    'ANNOUNCE'
  ),
  (
    '[2025_032] Thông báo lập điều chỉnh kế hoạch học tập cá nhân HK Hè Năm học 2024 - 2025',
    'https://file.hoasen.edu.vn/files/TB_28_lap_KHHT_ca_nhan_2434.pdf',
    NULL,
    'ANNOUNCE'
  ),
  (
    '[2025_031] Lịch thi Cuối kỳ Học phần EII 1, EII 2 (Giai đoạn 1) Học kỳ 2_Năm học 2024-2025',
    'https://file.hoasen.edu.vn/files/Lich_thi_CK_EII1_2_HK2433_GD1.rar',
    NULL,
    'ANNOUNCE'
  )
) AS v(title, link_url, image_link_url, category)
WHERE NOT EXISTS (
  SELECT 1
    FROM announcements a
   WHERE a.title = v.title
     AND COALESCE(a.link_url, '') = COALESCE(v.link_url, '')
);

-- Step 17: check result
SELECT
  id,
  title,
  link_url,
  image_link_url,
  category
FROM announcements
ORDER BY id DESC
LIMIT 11;

COMMIT;

