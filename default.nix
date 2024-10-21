let
  pkgs = import <nixpkgs> {};
in
{ stdenv ? pkgs.stdenv, fetchurl ? pkgs.fetchurl, makeWrapper ? pkgs.makeWrapper, jre ? pkgs.jre }:

stdenv.mkDerivation rec {
  name = "pske";
  version = "0.1";
  src = ./out/artifacts/PSKEditor_jar/PSKEditor.jar;
  dontUnpack = true;

  nativeBuildInputs = [ makeWrapper ];
  installPhase = ''
  mkdir -pv $out/share/java $out/bin $out/share/applications
  cp ${src} $out/share/java/${name}-${version}.jar
  makeWrapper ${jre}/bin/java $out/bin/${name} \
      --add-flags "-jar $out/share/java/${name}-${version}.jar" \
      --set _JAVA_OPTIONS '-Dawt.useSystemAAFontSettings=on' \
      --set _JAVA_AWT_WM_NONREPARENTING 1
  echo "
    [Desktop Entry]
    Encoding=UTF-8
    Version=1.0
    Type=Application
    NoDisplay=false
    Exec=$out/bin/${name}
    Name=PSKEditor
    Comment=A simple text editor written in Kotlin.
    Icon=kwrite
  " >> $out/share/applications/${name}.desktop
  '';

  # Some easy metadata, in case I forget.
#  meta = {
#    homepage = "";
#    description = "A simple text editor written in Kotlin.";
#    license = stdenv.lib.licenses.gpl3;
#    platforms = stdenv.lib.platforms.unix;
#    maintainers = [ stdenv.lib.maintainers.scolobb ];
#  };
}
