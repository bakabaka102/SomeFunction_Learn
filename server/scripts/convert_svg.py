# -*- coding: utf-8 -*-
import os
import sys
from xml.etree import ElementTree as ET

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
INPUT_DIR = os.path.abspath(os.path.join(BASE_DIR, "../src/main/assets"))
OUTPUT_DIR = os.path.abspath(os.path.join(BASE_DIR, "../src/main/res/drawable"))

os.makedirs(OUTPUT_DIR, exist_ok=True)

def convert_svg_to_vector(svg_path, output_path):
    try:
        tree = ET.parse(svg_path)
        root = tree.getroot()

        width = root.get('width', '24')
        height = root.get('height', '24')
        viewbox = root.get('viewBox')
        if viewbox:
            _, _, viewport_w, viewport_h = viewbox.strip().split()
        else:
            viewport_w = width
            viewport_h = height

        paths = []
        for elem in root.iter():
            tag = elem.tag.lower()
            if tag.endswith('path'):
                d = elem.get('d')
                fill = elem.get('fill', '#000000')
                if d:
                    paths.append((d, fill))
            elif tag.endswith('rect'):
                x = float(elem.get('x', '0'))
                y = float(elem.get('y', '0'))
                w = float(elem.get('width', '0'))
                h = float(elem.get('height', '0'))
                fill = elem.get('fill', '#000000')
                d = f"M{x} {y} H{x + w} V{y + h} H{x} Z"
                paths.append((d, fill))

        if not paths:
            print(f"[WARN] No path/rect found in {svg_path}")
            return

        with open(output_path, 'w', encoding='utf-8') as out:
            out.write(f'''<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="{width}dp"
    android:height="{height}dp"
    android:viewportWidth="{viewport_w}"
    android:viewportHeight="{viewport_h}">
''')
            for d, fill in paths:
                out.write(f'''    <path
        android:fillColor="{fill}"
        android:pathData="{d}" />
''')
            out.write('</vector>\n')

        print(f"[OK] Converted: {os.path.basename(svg_path)} -> {os.path.basename(output_path)}")

    except Exception as e:
        print(f"[FAIL] Failed to convert {svg_path}: {e}")

if __name__ == "__main__":
    try:
        svg_files = [f for f in os.listdir(INPUT_DIR) if f.endswith(".svg")]
        if not svg_files:
            print("[WARN] No SVG files found.")
            sys.exit(0)

        for filename in svg_files:
            input_path = os.path.join(INPUT_DIR, filename)
            output_filename = f"ic_{os.path.splitext(filename)[0]}.xml"
            output_path = os.path.join(OUTPUT_DIR, output_filename)
            convert_svg_to_vector(input_path, output_path)

        print("Done converting all SVGs.")

    except Exception as e:
        print(f"[FATAL] Script failed: {e}")
        import traceback
        traceback.print_exc()
        sys.exit(1)
